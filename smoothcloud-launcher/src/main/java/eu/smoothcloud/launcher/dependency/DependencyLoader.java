package eu.smoothcloud.launcher.dependency;

import java.io.File;

public class DependencyLoader {
    private static final String mainFolder = "Dependencys/";
    private static final String jarExt = ".jar";

    public static void loadExternalDependencys() {
        for (Dependency dep : Dependency.values()) {
            String link = MavenBuilder.buildMavenLink(dep);
            System.out.println("Try to Download " + link);
            String depDir = mainFolder + dep.getGroupId() + "." + dep.getArtifactId();
            String depFileName = dep.getVersion() + jarExt;
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

    public static void loadInternalDependencys() {
        for (InternalDependency inDep : InternalDependency.values()) {
            System.out.println("Try to Download " + inDep.getLink());
            String inDepDir = mainFolder + inDep.getName();
            String inDepFileName = inDep.getName() + jarExt;
            if(new File(inDepDir, inDepFileName).exists()) {
                System.out.println("Skip Dependency " + inDepFileName);
                continue;
            }
            Downloader.download(
                    inDep.getLink(),
                    inDepDir,
                    inDepFileName
            );
        }
    }
}
