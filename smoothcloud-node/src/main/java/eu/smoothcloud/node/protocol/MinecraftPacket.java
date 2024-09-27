package eu.smoothcloud.node.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface MinecraftPacket {

    void encode(byte[] out) throws IOException;
    void decode(byte[] in) throws IOException;
    byte[] toByteArray() throws IOException;
    int getPacketId();

}
