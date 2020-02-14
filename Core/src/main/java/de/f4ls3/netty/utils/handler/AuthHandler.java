package de.f4ls3.netty.utils.handler;

import de.f4ls3.netty.impl.Handler;

public class AuthHandler {

    private String authKey;

    public AuthHandler(String authKey) {
        this.authKey = authKey;
    }

    public boolean validateAuthKey(String key) {
        return key.equals(authKey);
    }

    public String getAuthKey() {
        return authKey;
    }
}
