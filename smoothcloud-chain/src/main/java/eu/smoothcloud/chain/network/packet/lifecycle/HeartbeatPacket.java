package eu.smoothcloud.chain.network.packet.lifecycle;

import eu.smoothcloud.chain.network.packet.SmoothPacket;
import io.netty5.buffer.Buffer;

public class HeartbeatPacket extends SmoothPacket {

    public final static int PACKET_TYPE = 100;

    /**
     * Methode zum Lesen eines Pakets.
     *
     * @param buffer Der Buffer, aus dem das Paket gelesen wird.
     */
    @Override
    public void read(Buffer buffer) {

    }

    /**
     * Methode zum Schreiben eines Pakets.
     *
     * @param buffer Der Buffer, in den das Paket geschrieben wird.
     */
    @Override
    public void write(Buffer buffer) {
        buffer.writeInt(1);
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public int getEstimatedSize() {
        return 8;
    }
}
