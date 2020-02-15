package de.f4ls3.netty.server;

import de.f4ls3.netty.utils.handler.FileHandler;

public class ServerLauncher {

    /**
     * @see de.f4ls3.netty.server.Server
     * @param args
     */
    public static void main(String[] args) {
        Server server = new Server(5780);
        server.start();
    }
}
