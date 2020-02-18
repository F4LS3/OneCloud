package de.f4ls3.netty.server;

import de.f4ls3.netty.server.commands.HelpCommand;
import de.f4ls3.netty.server.commands.PingCommand;
import de.f4ls3.netty.server.commands.StopCommand;
import de.f4ls3.netty.server.handler.PacketChannelInboundHandler;
import de.f4ls3.netty.server.handler.PacketDecoder;
import de.f4ls3.netty.server.handler.PacketEncoder;
import de.f4ls3.netty.utils.handler.CommandHandler;
import de.f4ls3.netty.utils.handler.FileHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

public class Server extends Thread {

    private static final boolean EPOLL = Epoll.isAvailable();

    private int port;

    private static FileHandler fileHandler;
    private static KeyManager keyManager;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        MultithreadEventLoopGroup parentGroup = EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        MultithreadEventLoopGroup childGroup = EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();

        try {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            final SslContext sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();

            ServerBootstrap sb = new ServerBootstrap();

            sb.group(parentGroup, childGroup);
            sb.channel(EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class);
            sb.childHandler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ChannelPipeline pipeline =  ch.pipeline();

                    pipeline
                            .addLast(sslCtx.newHandler(ch.alloc()))
                            .addLast("decoder", new PacketDecoder())
                            .addLast("encoder", new PacketEncoder())
                            .addLast(new PacketChannelInboundHandler());
                }

                @Override
                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                    cause.printStackTrace();
                }
            });
            sb.option(ChannelOption.SO_BACKLOG, 128);
            sb.childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = sb.bind(this.port).sync();

            fileHandler = new FileHandler();
            fileHandler.handle();
            keyManager = new KeyManager();
            keyManager.flush("be502fea-a47f-4e3c-8350-5e5276f09f77f0ad1875-5e78-4365-9ce5-8e43ff379dfb");

            CommandHandler commandHandler = new CommandHandler();
            commandHandler.registerCommand(new PingCommand());
            commandHandler.registerCommand(new StopCommand());
            commandHandler.registerCommand(new HelpCommand());
            commandHandler.handle();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

    public static FileHandler getFileHandler() {
        return fileHandler;
    }

    public static KeyManager getKeyManager() {
        return keyManager;
    }
}
