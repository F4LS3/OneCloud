package de.f4ls3.netty.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

public class Document {

    private JSONObject storage;

    public Document() {
        this.storage = new JSONObject();
    }

    public Document(HashMap<String, Object> map) {
        this.storage = new JSONObject();
        appendAll(map);
    }

    public Document appendAll(HashMap<String, Object> map) {
        if(!map.isEmpty()) {
            map.forEach((key, value) -> {
                storage.put(key, value);
            });
        }
        return this;
    }

    public Document clear() {
        this.storage.entrySet().clear();
        return this;
    }

    public Document put(String key, Object value) {
        this.storage.put(key, value);
        return this;
    }

    public Object get(String key) {
        return this.storage.get(key);
    }

    public String getString(String key) {
        return (String) this.storage.get(key);
    }

    public Integer getInteger(String key) {
        return (Integer) this.storage.get(key);
    }

    public boolean getBoolean(String key) {
        return (boolean) this.storage.get(key);
    }

    public double getDouble(String key) {
        return (double) this.storage.get(key);
    }

    public float getFloat(String key) {
        return (float) this.storage.get(key);
    }

    public void flush(File file) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(getStorage()));
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject getStorage() {
        return storage;
    }
}
