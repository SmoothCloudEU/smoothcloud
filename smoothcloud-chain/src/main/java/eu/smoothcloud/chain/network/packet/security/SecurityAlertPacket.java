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

package eu.smoothcloud.chain.network.packet.security;

import eu.smoothcloud.chain.network.packet.SmoothPacket;
import io.netty5.buffer.Buffer;

public class SecurityAlertPacket extends SmoothPacket {

    public final static int PACKET_TYPE = 704;

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
