package de.f4ls3.netty.utils.handler;

import de.f4ls3.netty.impl.Handler;
import de.f4ls3.netty.utils.Document;
import de.f4ls3.netty.utils.FileParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileHandler extends Handler {

    private List<File> files = new ArrayList<>();
    private List<Document> documents = new ArrayList<>();

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

    public Document getConfig() {
        return documents.get(0);
    }

    public Document getAuthKey() {
        return documents.get(1);
    }
}
