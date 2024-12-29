package eu.smoothcloud.launcher;

import eu.smoothcloud.launcher.dependency.*;
import eu.smoothcloud.launcher.process.ProcessStarter;
import eu.smoothcloud.launcher.util.Pair;

import java.util.Map;

public class SmoothCloudLauncher {

    public static void main(String[] args) {
        DependencyLoader.loadExternalDependencys();
        InternalDependencyLoader.loadInternalDependencys();
        System.out.println("Start Node Module!");
        Map<String, Pair<String, String>> manifest = DependencyUtil.getIntDepByManifest();
        ProcessStarter.startProcess(
                "dependencies/eu.smoothcloud/" +
                manifest.get("eu.smoothcloud").getKey() + "-" +
                        manifest.get("eu.smoothcloud").getValue() + ".jar"
        );
    }
}
