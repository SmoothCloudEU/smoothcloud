package eu.smoothcloud.node.protocol.packets;

import de.syntaxjason.core.network.channel.Channel;
import eu.smoothcloud.node.network.Network;
import eu.smoothcloud.node.network.entity.NetworkPinger;
import eu.smoothcloud.node.protocol.MinecraftPacket;
import eu.smoothcloud.node.protocol.ProtocolUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class HandshakePacket implements MinecraftPacket {

    private Channel channel;

    private int packetLength;

    public HandshakePacket(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void encode(byte[] out) throws IOException {
        int currentIndex = 0;

        out[currentIndex++] = (byte) 0x01;
        out[currentIndex++] = 0x00;

        currentIndex += ProtocolUtils.writeVarInt(763, out, currentIndex);
        int stringLength = ProtocolUtils.writeString("localhost", out, currentIndex);
        currentIndex += stringLength;

        out[currentIndex++] = (byte) 1;

        out[1] = (byte) (currentIndex - 2);
    }

    @Override
    public void decode(byte[] in) throws IOException {
        int offset = 0;
        int packetLength = in[offset++] & 0xFF;
        int packetId = in[offset++] & 0xFF;
        int protocolVersion = ProtocolUtils.readVarInt(in, offset);
        offset += ProtocolUtils.writeVarInt(protocolVersion, in, offset);
        String serverAddress = ProtocolUtils.readString(in, offset);
        offset += serverAddress.length();
        int serverPort = in[offset++] & 0xFF;
        int nextState = in[16] & 0xFF;

        NetworkPinger pinger = new NetworkPinger(channel.getRemoteAddress(), protocolVersion, nextState);
    }

    @Override
    public byte[] toByteArray() throws IOException {
        int baseLength = 2;
        int protocolVersionLength = ProtocolUtils.getVarIntLength(763);
        int serverAddressLength = ProtocolUtils.getStringLength("localhost");
        int additionalByteLength = 1;
        int totalLength = baseLength + protocolVersionLength + serverAddressLength + additionalByteLength;
        byte[] out = new byte[totalLength + 1];
        encode(out);
        return out;
    }

    @Override
    public int getPacketId() {
        return 0x00;
    }
}
