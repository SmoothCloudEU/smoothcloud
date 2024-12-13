package eu.smoothcloud.chain.network.packet;

import io.netty5.buffer.Buffer;

public class PacketUtils {

    /**
     * Reads a complete packet from the buffer.
     *
     * @param buffer The buffer from which the packet is read.
     * @param packet The packet to be read.
     */
    public static SmoothPacket decode(Buffer buffer, SmoothPacket packet) {
        int packetLength = readVarInt(buffer);
        if (buffer.readableBytes() < packetLength) {
            throw new IllegalArgumentException("Packet not readable!");
        }
        int protocolVersion = buffer.readInt();
        int packetType = buffer.readByte();

        packet.setProtocolVersion(protocolVersion);
        packet.setPacketType(packetType);

        packet.read(buffer);
        return definePacket(packet);
    }

    private static SmoothPacket definePacket(SmoothPacket packet) {
        return null;
    }

    /**
     * Writes a complete packet to the buffer.
     *
     * @param buffer The target buffer.
     * @param packet The packet to be written.
     */
    public static void encode(Buffer buffer, SmoothPacket packet) {
        try (Buffer tempBuffer = buffer.writeSplit(0)) {
            tempBuffer.writeInt(packet.getProtocolVersion());
            tempBuffer.writeInt(packet.getPacketType());
            packet.write(tempBuffer);

            int packetLength = tempBuffer.readableBytes();

            writeVarInt(packetLength, buffer);

            buffer.writeBytes(tempBuffer);
        }
    }

    /**
     * Reads a VarInt from the buffer.
     *
     * @param buffer The buffer.
     * @return The read VarInt value.
     */
    public static int readVarInt(Buffer buffer) {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = buffer.readByte();
            value |= (currentByte & 0x7F) << (position++ * 7);

            if (position > 5) {
                throw new IllegalArgumentException("VarInt is too large!");
            }
            if ((currentByte & 0x80) == 0) {
                break;
            }
        }
        return value;
    }

    /**
     * Writes a VarInt to the buffer.
     *
     * @param value  The value to be written.
     * @param buffer The target buffer.
     */
    public static void writeVarInt(int value, Buffer buffer) {
        while ((value & 0xFFFFFF80) != 0L) {
            buffer.writeByte((byte) ((value & 0x7F) | 0x80));
            value >>>= 7;
        }
        buffer.writeByte((byte) (value & 0x7F));
    }
}
