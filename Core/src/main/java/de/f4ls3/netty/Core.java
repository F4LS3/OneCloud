package de.f4ls3.netty;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Core {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static Gson getGson() {
        return GSON;
    }
}
