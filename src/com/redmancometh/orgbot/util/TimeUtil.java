package com.redmancometh.orgbot.util;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class TimeUtil
{

    public static void shortPause(Runnable r, ScheduledExecutorService service)
    {
        System.out.println("SHORT PAUSE!");
        service.schedule(() -> r.run(), ThreadLocalRandom.current().nextLong(100, 200), TimeUnit.MILLISECONDS);
    }

    public static void mediumPause(Runnable r, ScheduledExecutorService service)
    {
        service.schedule(() -> r.run(), ThreadLocalRandom.current().nextLong(450, 1500), TimeUnit.MILLISECONDS);
    }

    public static void longPause(Runnable r, ScheduledExecutorService service)
    {
        service.schedule(() -> r.run(), ThreadLocalRandom.current().nextLong(3, 5), TimeUnit.SECONDS);
    }
}
