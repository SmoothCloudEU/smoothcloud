package eu.smoothcloud.launcher.dependency;

import java.io.File;

public class DependencyLoader {
    private static final String MAIN_FOLDER = "Dependencys/";
    private static final String JAR_EXT = ".jar";

    public static void loadExternalDependencys() {
        for (Dependency dep : Dependency.values()) {
            String link = MavenBuilder.buildMavenLink(dep);
            String depDir = MAIN_FOLDER + dep.getGroupId() + "." + dep.getArtifactId();
            String depFileName = dep.getVersion() + JAR_EXT;
            System.out.println("Try to Download " + depFileName);
            if(new File(depDir, depFileName).exists()) {
                System.out.println("Skip Dependency " + depFileName);
                continue;
            }
            Downloader.download(
                    link,
                    depDir,
                    depFileName
            );
        }
    }
}
