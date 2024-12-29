package eu.smoothcloud.launcher.dependency;

import com.sun.tools.javac.Main;
import eu.smoothcloud.launcher.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class DependencyUtil {
    private static final String MAIN_FOLDER = "dependencies/";
    private static final String JAR_EXTENSION = ".jar";
    private static final String MANIFEST_URL = "https://cdn.smoothcloud.eu/dependencies/manifest.json";

    public static Map<String, Pair<String, String>> getIntDepByManifest() {
        Map<String, Pair<String, String>> manifest = new HashMap<>();
        try {
            File jarFile = new File(
                    MAIN_FOLDER + Dependency.JSON.getGroupId(),
                    Dependency.JSON.getArtifactId() + "-" + Dependency.JSON.getVersion() + JAR_EXTENSION
            );
            URL jarUrl = jarFile.toURI().toURL();

            StringBuilder jsonData = new StringBuilder();
            HttpURLConnection connection = (HttpURLConnection) new URL(MANIFEST_URL).openConnection();
            connection.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonData.append(line);
                }
            }

            try (URLClassLoader classLoader = new URLClassLoader(new URL[] { jarUrl }, Main.class.getClassLoader())) {
                Class<?> jsonArrayClass = classLoader.loadClass("org.json.JSONArray");
                Class<?> jsonObjectClass = classLoader.loadClass("org.json.JSONObject");

                Object jsonArray = jsonArrayClass.getDeclaredConstructor(String.class).newInstance(jsonData.toString());

                int length = (int) jsonArrayClass.getMethod("length").invoke(jsonArray);
                for (int i = 0; i < length; i++) {
                    Object jsonObject = jsonArrayClass.getMethod("get", int.class).invoke(jsonArray, i);

                    String groupId = (String) jsonObjectClass.getMethod("getString", String.class).invoke(jsonObject,
                            "groupId");
                    String artifactId = (String) jsonObjectClass.getMethod("getString", String.class).invoke(jsonObject,
                            "artifactId");
                    String version = (String) jsonObjectClass.getMethod("getString", String.class).invoke(jsonObject,
                            "version");
                    manifest.put(groupId, new Pair<>(artifactId, version));
                }
                return manifest;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
