package eu.smoothcloud.chain.network.packet.server;

import eu.smoothcloud.chain.network.packet.SmoothPacket;
import io.netty5.buffer.Buffer;

public class ServerStatusRequestPacket extends SmoothPacket {
    /**
     * Schätzt die Größe des Pakets (nur Payload).
     *
     * @return Die geschätzte Größe des Payloads in Bytes.
     */
    @Override
    public int getEstimatedSize() {
        return 0;
    }

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
}

