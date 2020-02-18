package de.f4ls3.netty;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.f4ls3.netty.utils.FileParser;

public class Core {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final FileParser FILE_PARSER = new FileParser();

    public static Gson getGson() {
        return GSON;
    }

    public static FileParser getFileParser() {
        return FILE_PARSER;
    }
}
