package de.f4ls3.netty.client;

import de.f4ls3.netty.client.handler.PacketDecoder;
import de.f4ls3.netty.client.handler.PacketEncoder;
import de.f4ls3.netty.impl.Packet;
import de.f4ls3.netty.packets.AuthPacket;
import de.f4ls3.netty.packets.PingPacket;
import de.f4ls3.netty.utils.Logger;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.util.concurrent.TimeUnit;

public class Client extends Thread {

    private static final boolean EPOLL = Epoll.isAvailable();

    private int port;
    private long startTime = -1;
    private long reconnectAttempts = 0;
    private int reconnectDelay = 10;
    private String host;
    private Bootstrap b;

    public Client(int port, String host) {
        this.port = port;
        this.host = host;
    }

    @Override
    public void run() {
        MultithreadEventLoopGroup childGroup = EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();

        try {
            final SslContext sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();

            this.b = new Bootstrap();

            this.b.group(childGroup);
            this.b.channel(EPOLL ? EpollSocketChannel.class : NioSocketChannel.class);
            this.b.handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();

                    pipeline.addLast(sslCtx.newHandler(ch.alloc(), host, port));
                    pipeline.addLast("decoder", new PacketDecoder());
                    pipeline.addLast("encoder", new PacketEncoder());
                    pipeline.addLast(new SimpleChannelInboundHandler<Packet>() {
                                private String prefix;
                                private Channel channel;

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    this.channel = ctx.channel();
                                    this.prefix = "[" + this.channel.remoteAddress().toString() + "/id=" + this.channel.id() + "] ";

                                    if(startTime < 0) {
                                        startTime = System.currentTimeMillis();
                                    }

                                    Logger.log("Connected to Server");

                                    this.channel.writeAndFlush(new AuthPacket("be502fea-a47f-4e3c-8350-5e5276f09f77f0ad1875-5e78-4365-9ce5-8e43ff379dfb"));
                                    Logger.log("Sent authorization");
                                }

                                @Override
                                public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                    Logger.log("Disconnected from Server");

                                    Logger.warn("Trying to reconnect in " + reconnectDelay + "s...");
                                }

                                @Override
                                public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                                    ctx.channel().eventLoop().schedule(() -> {
                                        reconnectAttempts++;
                                        Logger.log("Reconnecting... (attempt: " + reconnectAttempts + ")");
                                        connect();

                                    }, reconnectDelay, TimeUnit.SECONDS);
                                }

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
                                    if(packet instanceof PingPacket) {
                                        ctx.channel().writeAndFlush(new PingPacket(System.currentTimeMillis()));
                                    }
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    if(cause.getMessage().equalsIgnoreCase("Eine vorhandene Verbindung wurde vom Remotehost geschlossen")) return;
                                    cause.printStackTrace();
                                }
                            });
                }

                @Override
                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                    cause.printStackTrace();
                }
            });

            ChannelFuture f = this.b.connect(this.host, this.port);
            f.sync().channel().closeFuture().syncUninterruptibly();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            childGroup.shutdownGracefully();
        }
    }

    public void connect() {
        if(this.b == null) return;

        this.b.connect(this.host, this.port).addListener((ChannelFutureListener) channelFuture -> {
            if(channelFuture.cause() != null) {
                this.startTime = -1;
                channelFuture.cause().printStackTrace();
            }
        });
    }
}
