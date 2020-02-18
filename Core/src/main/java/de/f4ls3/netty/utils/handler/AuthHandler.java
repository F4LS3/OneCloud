package de.f4ls3.netty.utils.handler;

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
