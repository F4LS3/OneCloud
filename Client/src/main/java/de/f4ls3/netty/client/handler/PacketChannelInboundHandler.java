package de.f4ls3.netty.client.handler;

import de.f4ls3.netty.impl.ConfirmationType;
import de.f4ls3.netty.impl.Packet;
import de.f4ls3.netty.impl.RequestType;
import de.f4ls3.netty.packets.AuthPacket;
import de.f4ls3.netty.packets.ConfirmationPacket;
import de.f4ls3.netty.packets.PingPacket;
import de.f4ls3.netty.packets.RequestPacket;
import de.f4ls3.netty.utils.Logger;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PacketChannelInboundHandler extends SimpleChannelInboundHandler<Packet> {

    private String prefix;
    private Channel channel;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
        this.prefix = "[" + this.channel.remoteAddress().toString() + "/id=" + this.channel.id() + "] ";

        Logger.log("Connected to Server");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Logger.log("Disconnected from Server");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        if (packet instanceof RequestPacket) {
            RequestPacket requestPacket = (RequestPacket) packet;
            if(requestPacket.getRequestType().equals(RequestType.AUTH)) {
                this.channel.writeAndFlush(new AuthPacket("be502fea-a47f-4e3c-8350-5e5276f09f77f0ad1875-5e78-4365-9ce5-8e43ff379dfb"));
                Logger.log("Sent authorization");
            }

        } else if (packet instanceof PingPacket) {
            ctx.channel().writeAndFlush(new PingPacket(System.currentTimeMillis()));

        } else if (packet instanceof ConfirmationPacket) {
            ConfirmationPacket confirmationPacket = (ConfirmationPacket) packet;

            if (confirmationPacket.getConfirmationType().equals(ConfirmationType.AUTH)) {
                Logger.log("Authorized successfully");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause.getMessage().equalsIgnoreCase("Eine vorhandene Verbindung wurde vom Remotehost geschlossen")) return;
        cause.printStackTrace();
    }
}
