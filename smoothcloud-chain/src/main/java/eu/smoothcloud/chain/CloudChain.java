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

package eu.smoothcloud.chain;

import com.nexoscript.nexonet.server.Server;

public class CloudChain {
    private final int port;
    private final Server server;

    public CloudChain(int port) {
        this.port = port;
        this.server = new Server(true);
        this.server.onClientConnect(clientHandler -> {

        });
        this.server.onClientDisconnect(clientHandler -> {

        });
        this.server.onServerSend((clientHandler, packet) -> {

        });
        this.server.onClientDisconnect(clientHandler -> {

        });
        this.server.start(this.port);
    }
}
