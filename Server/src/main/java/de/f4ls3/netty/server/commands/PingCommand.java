package de.f4ls3.netty.server.commands;

import de.f4ls3.netty.packets.PingPacket;
import de.f4ls3.netty.impl.CommandExecutor;
import de.f4ls3.netty.impl.CommandInfo;
import de.f4ls3.netty.server.handler.PacketChannelInboundHandler;
import de.f4ls3.netty.utils.Logger;

@CommandInfo(name = "ping", aliases = {"pong", "ms"}, description = "Berechnet den Ping zwischen Server und Client", syntax = "ping")
public class PingCommand extends CommandExecutor {

    @Override
    public void execute(String[] args) {
        if(PacketChannelInboundHandler.getSessionUtils().getSessionByName("test") != null) {
            if(args.length > 1) {
                Logger.warn("Syntax: " + getSyntax());
                return;
            }

            PacketChannelInboundHandler.getSessionUtils().getSessionByName("test").getSessionContext().channel().writeAndFlush(new PingPacket(System.currentTimeMillis()));
        } else {
            Logger.err("No Current Session Available!");
        }
    }
}
