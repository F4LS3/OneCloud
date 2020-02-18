package de.f4ls3.netty.server;

import java.io.File;

public class KeyManager {

    public void flush(String authKey) {
        try {
            Server.getFileHandler().getAuthKey().put("key", authKey).flush(new File("./auth_key.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAuthKey() {
        return Server.getFileHandler().getAuthKey().getString("key");
    }
}
