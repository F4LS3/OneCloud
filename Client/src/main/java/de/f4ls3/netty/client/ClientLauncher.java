package de.f4ls3.netty.client;

public class ClientLauncher {

    /**
     * @see Client
     * @param args
     */
    public static void main(String[] args) {
        Client client = new Client(5780, "127.0.0.1");

        client.start();
    }
}
