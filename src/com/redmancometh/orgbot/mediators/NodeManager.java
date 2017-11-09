package com.redmancometh.orgbot.mediators;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import com.redmancometh.orgbot.config.tor.Config;
import com.redmancometh.orgbot.config.tor.TorHttpProxy;
import com.redmancometh.orgbot.config.tor.TorNode;
import com.redmancometh.tordeploy.TorDeploy;

import lombok.Data;

@Data
public class NodeManager
{
    private List<TorNode> inUse = new CopyOnWriteArrayList();
    private Config cfg;
    private AtomicInteger lastControlPort = new AtomicInteger(9051);
    private AtomicInteger lastDataPort = new AtomicInteger(9050);

    public void init()
    {
        this.cfg = TorDeploy.cfg();
        for (int x = 0; x < cfg.getBots(); x++)
        {
            try
            {
                System.out.println("X: " + x);
                TorNode node = cfg.getNodes().get(x);
                TorHttpProxy torProxy = new TorHttpProxy(node.getDataPort() + 5000, node.getDataPort());
                node.initialize();
                torProxy.initialize();
            }

            catch (Exception e)
            {
                e.printStackTrace();
                continue;
            }
        }
    }

}
