package de.f4ls3.netty.utils;

import de.f4ls3.netty.impl.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
    private static Log log = new Log(new File("./logs/latest.log"));

    public static void log(Object obj) {
        log.append(format.format(new Date()) + " | INFORMATION: " + obj).flush();
        System.out.println(format.format(new Date()) + " | INFORMATION: " + obj);
    }

    public static void warn(Object obj) {
        log.append(format.format(new Date()) + " | WARNING: " + obj).flush();
        System.out.println(format.format(new Date()) + " | WARNING: " + obj);
    }

    public static void err(Object obj) {
        log.append(format.format(new Date()) + " | ERROR: " + obj).flush();
        System.out.println(format.format(new Date()) + " | ERROR: " + obj);
    }

    public static Log getLog() {
        return log;
    }
}
