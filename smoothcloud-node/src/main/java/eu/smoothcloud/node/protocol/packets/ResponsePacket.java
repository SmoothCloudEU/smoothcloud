package eu.smoothcloud.node.protocol.packets;

import eu.smoothcloud.node.protocol.MinecraftPacket;
import eu.smoothcloud.node.protocol.ProtocolUtils;

import java.io.IOException;

public class ResponsePacket implements MinecraftPacket {
    @Override
    public void encode(byte[] out) throws IOException {
        int offset = 0;

        out[offset++] = (byte) 0x01;

        offset += ProtocolUtils.writeVarInt(763, out, offset);

        String jsonResponse = "{\"version\":{\"name\":\"1.20\",\"protocol\":763},\"players\":{\"max\":100,\"online\":5},\"description\":{\"text\":\"Welcome to the server!\"}";
        offset += ProtocolUtils.writeString(jsonResponse, out, offset);

        out[1] = (byte) (offset - 2);
    }

    @Override
    public void decode(byte[] in) throws IOException {
    }

    @Override
    public byte[] toByteArray() throws IOException {
        int baseLength = 2;
        int protocolVersionLength = ProtocolUtils.getVarIntLength(763);
        String jsonResponse = "{\"version\":{\"name\":\"1.20\",\"protocol\":763},\"players\":{\"max\":100,\"online\":5},\"description\":{\"text\":\"Welcome to the server!\"}";
        int stringLength = ProtocolUtils.getStringLength(jsonResponse);

        int totalLength = baseLength + protocolVersionLength + stringLength;

        byte[] out = new byte[totalLength + 1];
        encode(out);
        return out;
    }

    @Override
    public int getPacketId() {
        return 1;
    }
}
