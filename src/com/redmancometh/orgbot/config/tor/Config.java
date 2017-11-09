package com.redmancometh.orgbot.config.tor;

import java.util.List;

import lombok.Data;

@Data
public class Config
{
    private List<TorNode> nodes;
    private int bots;
    private boolean bindLocal;
    private String deployConfigs;
    private String defaultConfig;
    private String executable, executableName, torDir, delegateConfigDir, delegateDir, delegateExeucutable, delegateExecutableName;
}
