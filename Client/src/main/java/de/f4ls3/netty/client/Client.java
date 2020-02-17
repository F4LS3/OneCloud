package de.f4ls3.netty.client;

import de.f4ls3.netty.client.handler.PacketChannelInboundHandler;
import de.f4ls3.netty.client.handler.PacketDecoder;
import de.f4ls3.netty.client.handler.PacketEncoder;
import de.f4ls3.netty.impl.ConfirmationType;
import de.f4ls3.netty.impl.Packet;
import de.f4ls3.netty.packets.AuthPacket;
import de.f4ls3.netty.packets.ConfirmationPacket;
import de.f4ls3.netty.packets.PingPacket;
import de.f4ls3.netty.utils.Logger;
import de.f4ls3.netty.utils.handler.CommandHandler;
import de.f4ls3.netty.utils.handler.FileHandler;
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

import javax.net.ssl.SSLException;
import java.util.concurrent.TimeUnit;

public class Client extends Thread {

    private static final boolean EPOLL = Epoll.isAvailable();

    private int port;
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
                    pipeline.addLast(new PacketChannelInboundHandler());
                }
            });

            ChannelFuture f = this.b.connect(this.host, this.port);

            FileHandler fileHandler = new FileHandler();
            fileHandler.handle();

            f.sync().channel().closeFuture().syncUninterruptibly();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            childGroup.shutdownGracefully();
        }
    }
}