package eu.smoothcloud.launcher.dependency;

import com.sun.tools.javac.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;

public class InternalDepLoader {
    private static final String MAIN_FOLDER = "Dependencys/";
    private static final String JAR_EXT = ".jar";
    private static final String MANIFEST_URL = "https://cdn.smoothcloud.eu/dependencies/manifest.json";


    public static void loadInternalDependencys() {
        try {
            File jarFile = new File(
                    MAIN_FOLDER + Dependency.JSON.getGroupId() + "." + Dependency.JSON.getArtifactId(),
                    Dependency.JSON.getVersion() + JAR_EXT
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

                    String name = (String) jsonObjectClass.getMethod("getString", String.class).invoke(jsonObject,
                            "name");
                    String version = (String) jsonObjectClass.getMethod("getString", String.class).invoke(jsonObject,
                            "version");
                    String url = "https://cdn.smoothcloud.eu/dependencies/" + name + "-" + version + ".jar";

                    String inDepDir = MAIN_FOLDER + name;
                    String inDepFileName = name + "-" + version + JAR_EXT;
                    if(new File(inDepDir, inDepFileName).exists()) {
                        System.out.println("Skip Dependency " + inDepFileName);
                        continue;
                    }
                    File tempFile = new File(inDepDir);
                    if(tempFile.exists()) {
                        for (File file : tempFile.listFiles()) {
                            file.delete();
                        }
                    }
                    Downloader.download(
                            url,
                            inDepDir,
                            inDepFileName
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
