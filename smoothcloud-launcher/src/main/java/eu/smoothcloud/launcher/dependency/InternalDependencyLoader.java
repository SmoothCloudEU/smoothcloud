package eu.smoothcloud.launcher.dependency;

import eu.smoothcloud.launcher.SmoothCloudLauncher;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;

public class InternalDependencyLoader {
    private static final String MAIN_FOLDER = "dependencies/";
    private static final String JAR_EXTENSION = ".jar";
    private static final String MANIFEST_URL = "https://cdn.smoothcloud.eu/dependencies/manifest.json";


    public static void loadInternalDependencys() {
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

            try (URLClassLoader classLoader = new URLClassLoader(new URL[] { jarUrl }, SmoothCloudLauncher.class.getClassLoader())) {
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
                    String url = "https://cdn.smoothcloud.eu/dependencies/" + artifactId + "-" + version + ".jar";

                    String inDepDir = MAIN_FOLDER + groupId;
                    String inDepFileName = artifactId + "-" + version + JAR_EXTENSION;
                    if(new File(inDepDir, inDepFileName).exists()) {
                        System.out.println("Skipped " + inDepFileName);
                        continue;
                    }
                    File tempFile = new File(inDepDir);
                    if(tempFile.exists()) {
                        for (File file : Objects.requireNonNull(tempFile.listFiles())) {
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
            throw new RuntimeException(e);
        }
    }
}
