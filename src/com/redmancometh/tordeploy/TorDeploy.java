package com.redmancometh.tordeploy;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import com.redmancometh.orgbot.config.ConfigManager;
import com.redmancometh.orgbot.config.tor.Config;
import com.redmancometh.orgbot.mediators.NodeManager;

public class TorDeploy
{
    private static ScheduledExecutorService pool = Executors.newScheduledThreadPool(32);
    private static ConfigManager cfgMan;
    private static NodeManager nodes;

    public static void main(String[] args)
    {
        cfgMan = new ConfigManager();
        cfgMan.init();
        nodes = new NodeManager();
        nodes.init();
    }

    public static ScheduledExecutorService getPool()
    {
        return pool;
    }

    public static void setPool(ScheduledExecutorService pool)
    {
        TorDeploy.pool = pool;
    }

    public static Config cfg()
    {
        return cfgMan.getConfig();
    }

}
