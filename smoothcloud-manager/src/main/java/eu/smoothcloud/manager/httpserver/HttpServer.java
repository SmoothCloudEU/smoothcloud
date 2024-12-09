package eu.smoothcloud.manager.httpserver;

import eu.smoothcloud.manager.httpserver.api.DocumentationServlet;
import eu.smoothcloud.manager.httpserver.api.get.player.ListServlet;
import eu.smoothcloud.manager.httpserver.api.get.player.PlayerServlet;
import lombok.Getter;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.server.Server;

@Getter
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
