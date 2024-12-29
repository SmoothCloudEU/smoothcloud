package eu.smoothcloud.launcher;

import eu.smoothcloud.launcher.dependency.*;

public class SmoothCloudLauncher {

    public static void main(String[] args) {
        DependencyLoader.loadExternalDependencys();
        System.out.println("Finished Downloading Default Dependencys");
        System.out.println("Begin Download for Internal Dependencys");
        InternalDepLoader.loadInternalDependencys();
        System.out.println("Finished Dependency Downloading!");
    }
}