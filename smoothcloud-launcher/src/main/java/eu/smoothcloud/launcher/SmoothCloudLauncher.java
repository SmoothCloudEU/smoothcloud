package eu.smoothcloud.launcher;

import eu.smoothcloud.launcher.dependency.*;

public class SmoothCloudLauncher {

    public static void main(String[] args) {
        DependencyLoader.loadExternalDependencys();
        InternalDependencyLoader.loadInternalDependencys();
    }
}
