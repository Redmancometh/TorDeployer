package com.redmancometh.orgbot.config.tor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import com.redmancometh.tordeploy.TorDeploy;

public class AutoDeployNode extends TorNode
{

    @Override
    public void initialize()
    {
        initConfigs();
        super.initialize();
    }

    public void initConfigs()
    {
        Config cfg = TorDeploy.cfg();
        checkDeployDir();
        String confPath = cfg.getDeployConfigs() + File.separator + "tor." + this.getDataPort() + File.separator + "torcfgtorrc." + this.getDataPort();
        File f = new File(confPath);
        if (!f.exists()) writeConfig(f);
        startProxy(confPath);
    }

    public void checkDeployDir()
    {
        Config cfg = TorDeploy.cfg();
        String deployDir = cfg.getDeployConfigs();
        File f = new File(deployDir);
        if (!f.exists()) f.mkdir();
        File fIn = new File(deployDir + File.separator + "tor." + this.getDataPort());
        if (!fIn.exists()) fIn.mkdir();
        File torExe = new File(executablePath());
        if (!torExe.exists()) try
        {
            FileUtils.copyDirectory(new File(cfg.getTorDir()), fIn);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void writeConfig(File output)
    {
        try (Scanner s = new Scanner(new File(TorDeploy.cfg().getDefaultConfig())))
        {
            try (BufferedWriter w = new BufferedWriter(new FileWriter(output)))
            {
                while (s.hasNextLine())
                {
                    String line = s.nextLine();
                    String newLine = line.replace("ControlPort 9051", "ControlPort 0.0.0.0:" + getControlPort()).replace("SOCKSPort 9050", "SOCKSPort 0.0.0.0:" + getDataPort()) + "\n";
                    newLine = newLine.replace("HTTPProxy 0.0.0.0:8080", "HTTPProxy 0.0.0.0:" + (getControlPort() + 5000));
                    w.write(newLine.replace("DataDirectory C:/example", "DataDirectory " + cfgPath()));
                    w.flush();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
        }
    }

    public String cfgPath()
    {
        String deployDir = TorDeploy.cfg().getDeployConfigs().replace("//", File.separator);
        return deployDir + File.separator + "tor." + this.getDataPort() + File.separator;
    }

    public String executablePath()
    {
        return cfgPath() + TorDeploy.cfg().getExecutableName();
    }

    public void startProxy(String confPath)
    {
        System.out.println("START PROXY");
        try
        {
            Process p = new ProcessBuilder(executablePath(), "-f", confPath).start();
            BufferedReader buff = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = buff.readLine()) != null)
            {
                if (line.contains("Opening Control listener")) break;
            }
            System.out.println(p);
            System.out.println(TorDeploy.cfg().getExecutable());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
