/*
 * Copyright (c) 2024-2025 SmoothCloud team & contributors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.smoothcloud.chain.network.packet;

import io.netty5.buffer.Buffer;

public class PacketUtils {

    /**
     * Reads a complete packet from the buffer.
     *
     * @param buffer The buffer from which the packet is read.
     * @param packet The packet to be read.
     */
    public static void decode(Buffer buffer, SmoothPacket packet) {
        int packetLength = readVarInt(buffer);
        if (buffer.readableBytes() < packetLength) {
            throw new IllegalArgumentException("Packet not readable!");
        }
        int protocolVersion = buffer.readInt();
        int packetType = buffer.readByte();

        packet.setProtocolVersion(protocolVersion);
        packet.setPacketType(packetType);

        packet.read(buffer);
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
