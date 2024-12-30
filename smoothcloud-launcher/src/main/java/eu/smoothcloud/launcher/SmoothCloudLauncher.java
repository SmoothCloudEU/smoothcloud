package eu.smoothcloud.launcher;

import eu.smoothcloud.launcher.dependency.DependencyLoader;
import eu.smoothcloud.launcher.util.JarLoader;

import java.nio.file.Path;

public class SmoothCloudLauncher {
    private static SmoothCloudClassLoader classLoader;
    private static DependencyLoader dependencyLoader;
    private static JarLoader jarLoader;

    public static void main(String[] args) {
        classLoader = new SmoothCloudClassLoader();
        dependencyLoader = new DependencyLoader("dependencies");
        dependencyLoader.loadDependencys();
        System.out.println("Start Node Module!");
        jarLoader = new JarLoader();
        jarLoader.loadJar(Path.of("dependencies/eu.smoothcloud/smoothcloud-node-1.0.0-dev.jar"), args);
    }

    public static SmoothCloudClassLoader getClassLoader() {
        return classLoader;
    }

    public static DependencyLoader getDependencyLoader() {
        return dependencyLoader;
    }

    public static JarLoader getJarLoader() {
        return jarLoader;
    }
}
