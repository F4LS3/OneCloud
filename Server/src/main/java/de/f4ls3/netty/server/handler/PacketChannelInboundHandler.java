package de.f4ls3.netty.server.handler;

import de.f4ls3.netty.Core;
import de.f4ls3.netty.impl.ConfirmationType;
import de.f4ls3.netty.impl.Packet;
import de.f4ls3.netty.impl.RequestType;
import de.f4ls3.netty.impl.Session;
import de.f4ls3.netty.packets.AuthPacket;
import de.f4ls3.netty.packets.ConfirmationPacket;
import de.f4ls3.netty.packets.PingPacket;
import de.f4ls3.netty.packets.RequestPacket;
import de.f4ls3.netty.server.KeyManager;
import de.f4ls3.netty.server.Server;
import de.f4ls3.netty.utils.Logger;
import de.f4ls3.netty.utils.SessionUtils;
import de.f4ls3.netty.utils.handler.AuthHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class PacketChannelInboundHandler extends SimpleChannelInboundHandler<Packet> {

    private String prefix;
    private static final SessionUtils sessionUtils = new SessionUtils();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Session session = sessionUtils.newSession("test", ctx.channel().id().toString(), UUID.randomUUID(), ctx, false);
        sessionUtils.getSessions().add(session);
        this.prefix = "[" + session.getSessionContext().channel().remoteAddress().toString() + "/id=" + session.getSessionId() + "/uuid=" + session.getSessionUUID().toString() + "]";

        Logger.log("Channel " + this.prefix + " connected");
        session.getSessionContext().channel().writeAndFlush(new RequestPacket(RequestType.AUTH), session.getSessionContext().channel().voidPromise());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        sessionUtils.getSessions().remove(sessionUtils.getSessionById(ctx.channel().id().toString()));
        Logger.log("Channel " + this.prefix + " disconnected");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        Session session = sessionUtils.getSessionById(ctx.channel().id().toString());

        if(packet instanceof AuthPacket) {
            AuthHandler authHandler = new AuthHandler(Server.getKeyManager().getAuthKey());
            String authKey = ((AuthPacket) packet).getAuthKey();
            if(!authHandler.validateAuthKey(authKey)) {
                session.getSessionContext().channel().close();
                sessionUtils.getSessions().remove(session);
                return;

            } else {
                session.setAuthorized(authHandler.validateAuthKey(authKey));
                session.getSessionContext().channel().writeAndFlush(new ConfirmationPacket(ConfirmationType.AUTH));
                Logger.log("Channel " + this.prefix + " authorized successful");
                return;
            }

        } else if(session.isAuthorized()) {
            if(packet instanceof PingPacket) {
                long ping = (System.currentTimeMillis() - ((PingPacket) packet).getPing());
                Logger.log("Ping: " + ping + "ms");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(cause.getMessage().equalsIgnoreCase("Eine vorhandene Verbindung wurde vom Remotehost geschlossen")) return;

        cause.printStackTrace();
    }

    public static SessionUtils getSessionUtils() {
        return sessionUtils;
    }
}
