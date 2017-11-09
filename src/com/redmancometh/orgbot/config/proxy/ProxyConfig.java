package com.redmancometh.orgbot.config.proxy;

import java.util.List;

import lombok.Data;

@Data
public class ProxyConfig
{
    private List<Proxy> proxies;
    private int bots;
}
