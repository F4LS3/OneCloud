package de.f4ls3.netty.server.handler;

import de.f4ls3.netty.impl.Packet;
import de.f4ls3.netty.utils.Logger;
import de.f4ls3.netty.utils.PacketUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> list) throws Exception {
        byte[] bytes = PacketUtils.toByteArray(buffer);

        Packet packet = PacketUtils.deserialize(bytes);

        buffer.readBytes(buffer.readableBytes());
        list.add(packet);
    }
}
