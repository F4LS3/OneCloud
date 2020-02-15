package de.f4ls3.netty.utils.handler;

import de.f4ls3.netty.impl.Handler;
import de.f4ls3.netty.impl.Log;
import de.f4ls3.netty.utils.Document;
import de.f4ls3.netty.utils.FileParser;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FileHandler extends Handler {

    private List<File> files = new ArrayList<>();
    private List<Document> documents = new ArrayList<>();

    private static SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss_dd.MM.yyyy");
    private static Log log = new Log(new File("./logs/" + format.format(new Date()).toUpperCase() + ".log"));

    @Override
    public void handle() {
        try {
            FileParser parser = new FileParser();

            new File("./config.json").createNewFile();
            new File("./auth_key.token").createNewFile();

            files.add(0, new File("./config.json"));
            files.add(1, new File("./auth_key.token"));

            documents.add(0, new Document());
            documents.add(1, new Document());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveLatestLog() {
        try {
            List<Object> latest = new ArrayList<>();
            File file = new File("./logs/latest.log");
            if(!new File("./logs/latest.log").exists()) {
                new File("./logs/").mkdirs();
                file.createNewFile();
            }
            Scanner scanner = new Scanner(new File("./logs/latest.log"));
            while (scanner.hasNext()) {
                latest.add(scanner.nextLine());
            }
            log.appendAll(latest).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Document getConfig() {
        return documents.get(0);
    }

    public Document getAuthKey() {
        return documents.get(1);
    }
}
