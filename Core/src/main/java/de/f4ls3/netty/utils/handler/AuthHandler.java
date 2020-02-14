package de.f4ls3.netty.utils.handler;

import de.f4ls3.netty.impl.Handler;

public class AuthHandler extends Handler {

    private String authKey;

    public AuthHandler(String authKey) {
        this.authKey = authKey;
    }

    @Override
    public void handle() {
        
    }

    public String getAuthKey() {
        return authKey;
    }
}
