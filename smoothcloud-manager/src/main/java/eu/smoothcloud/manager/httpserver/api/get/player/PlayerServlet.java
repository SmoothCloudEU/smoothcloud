package eu.smoothcloud.manager.httpserver.api.get.player;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getPathInfo();
        String URL_PATTERN = "^/([a-zA-Z0-9\\-]+)(/.*)?$";
        Pattern pattern = Pattern.compile(URL_PATTERN);
        Matcher matcher = pattern.matcher(path);
        String uuid = null;
        String extraPath = null;
        if (matcher.matches()) {
            uuid = matcher.group(1);
            extraPath = matcher.group(2);
        }
        if (uuid == null || uuid.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid or missing UUID\"}");
            return;
        }
        if (extraPath == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid", uuid);
            jsonObject.put("details", "Some player details from the cloud");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonObject.toString());
            return;
        }
        switch (extraPath.toLowerCase()) {
            case "/namemc" -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uuid", uuid);
                jsonObject.put("details", "Some player details from namemc");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonObject.toString());
            }
            default -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("details", "invalid path");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonObject.toString());
            }
        }
    }
}
