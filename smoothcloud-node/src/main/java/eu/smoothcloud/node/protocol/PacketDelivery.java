package eu.smoothcloud.node.protocol;

import de.syntaxjason.core.network.channel.Channel;
import de.syntaxjason.core.protocol.Packet;

import java.io.IOException;
import java.util.Arrays;

public class PacketDelivery {

    private final Channel channel;
    private final MinecraftPacket[] packets;

    public PacketDelivery(Channel channel, MinecraftPacket... packets) {
        this.channel = channel;
        this.packets = packets;
    }

    public void sendPacket(int packetId) throws IOException {
        for (MinecraftPacket packet : packets) {
            if (packet.getPacketId() == packetId) {
                byte[] packetBytes = packet.toByteArray();
                byte[] header = new byte[] {(byte) packetBytes.length, (byte) packetId};
                byte[] fullPacket = new byte[header.length + packetBytes.length];
                System.arraycopy(header, 0, fullPacket, 0, header.length);
                System.arraycopy(packetBytes, 0, fullPacket, header.length, packetBytes.length);
                System.out.println("Sending packet: " + packetId + " with bytes: " + Arrays.toString(fullPacket));
                channel.send(Packet.createPacket(fullPacket));
                return;
            }
        }
    }

    public void sendAll() throws IOException {
        for (MinecraftPacket packet : packets) {
            channel.send(Packet.createPacket(packet.toByteArray()));
        }
    }
}

