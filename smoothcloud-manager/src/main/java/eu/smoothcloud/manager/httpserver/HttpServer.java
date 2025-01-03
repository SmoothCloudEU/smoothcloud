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

package eu.smoothcloud.manager.httpserver;

import eu.smoothcloud.manager.httpserver.api.DocumentationServlet;
import eu.smoothcloud.manager.httpserver.api.get.player.ListServlet;
import eu.smoothcloud.manager.httpserver.api.get.player.PlayerServlet;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.server.Server;

public class HttpServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        handler.setContextPath("/");
        handler.addServlet(DocumentationServlet.class, "/api");
        handler.addServlet(ListServlet.class, "/api/get/player/list");
        handler.addServlet(PlayerServlet.class, "/api/get/player/*");
        server.setHandler(handler);
        server.start();
        server.join();
    }
}
