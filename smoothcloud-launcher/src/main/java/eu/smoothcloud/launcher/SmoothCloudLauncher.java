package eu.smoothcloud.launcher;

import eu.smoothcloud.launcher.dependency.DependencyLoader;
import eu.smoothcloud.launcher.dependency.InternalDependencyLoader;
import eu.smoothcloud.launcher.util.JarLoader;

import java.nio.file.Path;

public class SmoothCloudLauncher {

    public static void main(String[] args) {
        DependencyLoader.loadExternalDependencys();
        InternalDependencyLoader.loadInternalDependencys();
        System.out.println("Start Node Module!");
        JarLoader.loadJar(Path.of("dependencies/eu.smoothcloud/smoothcloud-node-1.0.0-dev.jar"), args);
    }
}
