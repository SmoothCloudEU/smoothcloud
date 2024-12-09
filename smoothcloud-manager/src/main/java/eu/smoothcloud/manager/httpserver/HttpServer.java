package eu.smoothcloud.manager.httpserver;

import eu.smoothcloud.manager.httpserver.api.Documentation;
import lombok.Getter;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.server.Server;

@Getter
public class HttpServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath("/");
        handler.addServlet(Documentation.class, "/api/#/");
        server.setHandler(handler);
        server.start();
        server.join();
    }
}
