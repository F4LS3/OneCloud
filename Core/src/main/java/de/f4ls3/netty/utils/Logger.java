package de.f4ls3.netty.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");

    public static void log(Object obj) {
        System.out.println(format.format(new Date()) + " | INFORMATION: " + obj);
    }

    public static void warn(Object obj) {
        System.out.println(format.format(new Date()) + " | WARNING: " + obj);
    }

    public static void err(Object obj) {
        System.out.println(format.format(new Date()) + " | ERROR: " + obj);
    }
}
