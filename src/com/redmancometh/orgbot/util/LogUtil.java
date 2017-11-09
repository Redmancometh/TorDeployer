package com.redmancometh.orgbot.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogUtil
{
    public static void logPage(String source, String path)
    {
        try (FileWriter w = new FileWriter(new File(path)))
        {
            w.write(source);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
