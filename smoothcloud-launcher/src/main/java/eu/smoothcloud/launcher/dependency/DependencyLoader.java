package eu.smoothcloud.launcher.dependency;

import java.io.File;

public class DependencyLoader {
    private static final String MAIN_FOLDER = "dependencies/";
    private static final String JAR_EXTENSION = ".jar";

    public static void loadExternalDependencys() {
        for (Dependency dependency : Dependency.values()) {
            String link = MavenCentral.buildLink(dependency);
            String dependencyDir = MAIN_FOLDER + dependency.getGroupId();
            String dependencyName = dependency.getArtifactId() + "-" + dependency.getVersion() + JAR_EXTENSION;
            if(new File(dependencyDir, dependencyName).exists()) {
                System.out.println("Skipped " + dependencyName);
                continue;
            }
            Downloader.download(
                    link,
                    dependencyDir,
                    dependencyName
            );
        }
    }
}
