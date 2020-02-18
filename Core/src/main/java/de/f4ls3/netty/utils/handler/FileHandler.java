package de.f4ls3.netty.utils.handler;

import de.f4ls3.netty.Core;
import de.f4ls3.netty.impl.Handler;
import de.f4ls3.netty.impl.Log;
import de.f4ls3.netty.utils.Document;
import de.f4ls3.netty.utils.FileParser;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileHandler extends Handler {

    private List<File> files = new ArrayList<>();
    private List<Document> documents = new ArrayList<>();

    private static SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss_dd.MM.yyyy");
    private static Log log = new Log(new File("./logs/" + format.format(new Date()).toUpperCase() + ".log"));

    @Override
    public void handle() {
        try {
            FileParser parser = new FileParser();

            files.add(0, new File("./config.json"));
            files.add(1, new File("./auth_key.json"));
            files.add(2, new File("./logs/"));

            for (File file : files) {
                if(file.isDirectory() || !file.getName().endsWith(".json")) {
                    file.mkdirs();

                } else {
                    file.createNewFile();
                }
            }

            documents.add(0, new Document(Core.getGson().fromJson(parser.parseFile(files.get(0)), HashMap.class)));
            documents.add(1, new Document());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Log getLog() {
        return log;
    }

    public Document getConfig() {
        return documents.get(0);
    }

    public Document getAuthKey() {
        return documents.get(1);
    }
}
