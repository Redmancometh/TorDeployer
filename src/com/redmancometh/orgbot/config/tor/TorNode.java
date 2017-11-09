package com.redmancometh.orgbot.config.tor;

import lombok.Data;

@Data
public class TorNode
{
    private int dataPort;
    private int controlPort;
    private String address;

    public void initialize()
    {

    }

}
