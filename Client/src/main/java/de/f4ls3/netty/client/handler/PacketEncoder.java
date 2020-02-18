package de.f4ls3.netty.client.handler;

import de.f4ls3.netty.interfaces.Packet;
import de.f4ls3.netty.utils.PacketUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf buffer) throws Exception {
        byte[] bytes = PacketUtils.serialize(packet);

        buffer.writeBytes(bytes);
    }
}
