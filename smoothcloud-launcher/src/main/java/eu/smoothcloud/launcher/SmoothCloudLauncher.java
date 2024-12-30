package eu.smoothcloud.launcher;

import eu.smoothcloud.launcher.dependency.DependencyHandler;
import eu.smoothcloud.launcher.loader.JarLoader;
import eu.smoothcloud.launcher.loader.JsonLoader;

import java.io.IOException;

public class SmoothCloudLauncher {
    private static SmoothCloudClassLoader classLoader;
    private static DependencyHandler dependencyHandler;
    private static JsonLoader jsonLoader;
    private static JarLoader jarLoader;

    public static void main(String[] args) throws IOException {
        classLoader = new SmoothCloudClassLoader();
        dependencyHandler = new DependencyHandler();
        jsonLoader = new JsonLoader("https://github.com/SmoothCloudEU/smoothcloud-manifest/raw/refs/heads/master/dependencyLoader.json");
        jsonLoader.processJsonAndDownload();
        System.out.println("Starting smoothcloud-node...");
        jarLoader = new JarLoader();
        System.out.println("SMOOTHCLOUD-NODE: " + dependencyHandler.getDependencyPaths().get("smoothcloud-node"));
        jarLoader.loadJar(dependencyHandler.getDependencyPaths().get("smoothcloud-node"), args);
    }

    public static SmoothCloudClassLoader getClassLoader() {
        return classLoader;
    }

    public static DependencyHandler getDependencyHandler() {
        return dependencyHandler;
    }

    public static JsonLoader getJsonLoader() {
        return jsonLoader;
    }

    public static JarLoader getJarLoader() {
        return jarLoader;
    }
}
