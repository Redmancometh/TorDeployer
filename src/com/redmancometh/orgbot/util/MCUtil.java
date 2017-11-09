package com.redmancometh.orgbot.util;

import org.apache.commons.lang3.RandomStringUtils;

public class MCUtil
{
    public static String randomUsername()
    {
        return RandomStringUtils.randomAlphanumeric(12);
    }
}
