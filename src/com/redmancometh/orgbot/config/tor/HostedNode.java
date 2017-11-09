package com.redmancometh.orgbot.config.tor;

import com.redmancometh.orgbot.util.TorUtil;

public class HostedNode extends TorNode
{
    @Override
    public void initialize()
    {
        super.initialize();
        TorUtil.torNewIP(getAddress(), getControlPort());
    }
}
