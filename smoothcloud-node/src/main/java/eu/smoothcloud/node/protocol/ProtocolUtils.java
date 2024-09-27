package eu.smoothcloud.node.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ProtocolUtils {

    public static int readVarInt(byte[] data, int offset) throws IOException {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = data[offset + numRead];
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                throw new IOException("VarInt is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public static int writeVarInt(int value, byte[] out, int offset) {
        int numWritten = 0;
        do {
            byte temp = (byte) (value & 0b01111111);
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            out[offset + numWritten] = temp;
            numWritten++;
        } while (value != 0);

        return numWritten;
    }

    public static String readString(byte[] data, int offset) throws IOException {
        int length = readVarInt(data, offset);
        byte[] bytes = new byte[length];
        System.arraycopy(data, offset + 1, bytes, 0, length);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static int writeString(String string, byte[] out, int offset) throws IOException {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        int lengthBytes = writeVarInt(bytes.length, out, offset);
        System.arraycopy(bytes, 0, out, offset + lengthBytes, bytes.length);
        return lengthBytes + bytes.length;
    }

    public static int writeStatus(byte[] out, int offset) throws IOException {
        String jsonResponse = "{\"version\":{\"name\":\"1.21\",\"protocol\":767},"
                + "\"players\":{\"max\":100,\"online\":5},"
                + "\"description\":{\"text\":\"Welcome to the server!\"}}";

        byte[] jsonBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
        out[offset] = (byte) jsonBytes.length; // write packet length
        System.arraycopy(jsonBytes, 0, out, offset + 1, jsonBytes.length);
        return jsonBytes.length + 1;
    }

    public static int getVarIntLength(int value) {
        int length = 0;
        while ((value & ~0x7F) != 0) {
            length++;
            value >>>= 7;
        }
        return length + 1;
    }

    public static int getStringLength(String str) {
        return str.length();
    }
}
