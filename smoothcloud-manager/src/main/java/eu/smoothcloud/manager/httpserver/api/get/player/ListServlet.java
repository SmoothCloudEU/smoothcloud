package eu.smoothcloud.manager.httpserver.api.get.player;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;

import java.io.IOException;

public class ListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // TODO: Get player-list
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("player1");
        jsonArray.put("player2");
        jsonArray.put("player5");
        jsonArray.put("player3");
        response.getWriter().write(jsonArray.toString());
    }
}
