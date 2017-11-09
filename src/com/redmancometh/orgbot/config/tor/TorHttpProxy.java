package com.redmancometh.orgbot.config.tor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;

import com.redmancometh.tordeploy.TorDeploy;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TorHttpProxy
{
    private int proxyPort;
    private int torPort;

    public void initialize()
    {
        System.out.println("INITIALIZE!");
        initConfigs();
    }

    public void initConfigs()
    {
        Config cfg = TorDeploy.cfg();
        File dir = new File(cfg.getDelegateConfigDir());
        File sourecDir = new File(cfg.getDelegateDir());
        if (!dir.exists()) dir.mkdirs();
        File f = new File(cfgPath());
        if (!f.exists())
        {
            try
            {
                FileUtils.copyDirectory(sourecDir, f);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        startProxy();
    }

    public String cfgPath()
    {
        String deployDir = TorDeploy.cfg().getDelegateConfigDir() + File.separator + "delegate." + this.getProxyPort() + File.separator;
        return deployDir;
    }

    public String executablePath()
    {
        return cfgPath() + TorDeploy.cfg().getDelegateExecutableName();
    }

    private void startProxy()
    {
        System.out.println("START PROXY");
        try
        {
            System.out.println(executablePath());
            Process p = new ProcessBuilder(executablePath(), "-P" + this.getProxyPort(), "PERMIT=*:*:*", "SERVER=http", "SOCKS=localhost:" + this.getTorPort()).start();
            BufferedReader errBuff = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String errline;
            while ((errline = errBuff.readLine()) != null)
            {
                System.out.println(errline);
            }
            //-P8080 SERVER=http SOCKS=localhost:9050
            //PERMIT=*:*
            BufferedReader buff = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = buff.readLine()) != null)
            {
                System.out.println(line);
            }
            System.out.println(p.isAlive());
            System.out.println(TorDeploy.cfg().getExecutable());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
