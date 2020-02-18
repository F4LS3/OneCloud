package de.f4ls3.netty.utils;

import de.f4ls3.netty.interfaces.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

import java.io.*;

public class PacketUtils {

    public static byte[] serialize(Packet p) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(p);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return bytes;
    }

    public static Packet deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Packet p = (Packet) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return p;
    }

    public static byte[] toByteArray(ByteBuf buffer) {
        if(buffer.hasArray()) {
            return buffer.array();
        }

        return ByteBufUtil.getBytes(buffer);
    }
}
