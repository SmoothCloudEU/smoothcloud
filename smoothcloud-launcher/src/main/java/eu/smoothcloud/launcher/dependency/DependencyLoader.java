package eu.smoothcloud.launcher.dependency;

import eu.smoothcloud.launcher.SmoothCloudLauncher;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;

public class DependencyLoader {
    private final String directory;

    public DependencyLoader(String directory) {
        this.directory = directory + "/";
    }

    public void loadDependencys() {
        for (Dependency dependency : Dependency.values()) {
            String link = buildLink(dependency);
            String dependencyDir = directory + dependency.getGroupId();
            String dependencyName = dependency.getArtifactId() + "-" + dependency.getVersion() + ".jar";
            Path path = Path.of(directory + dependency.getGroupId() + "/" + dependency.getArtifactId() + "-" + dependency.getVersion() + ".jar");
            if(new File(dependencyDir, dependencyName).exists()) {
                System.out.println("Skipped " + dependencyName);
                try {
                    SmoothCloudLauncher.getClassLoader().addURL(path.toUri().toURL());
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }
            Downloader.download(
                    link,
                    dependencyDir,
                    dependencyName
            );
            try {
                SmoothCloudLauncher.getClassLoader().addURL(path.toUri().toURL());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String buildLink(Dependency dependency) {
        switch (dependency.getRepository()) {
            case SMOOTHCLOUD_CDN -> {
                return String.format(
                        Repository.SMOOTHCLOUD_CDN.getLink(),
                        dependency.getGroupId().replace(".", "/"),
                        dependency.getArtifactId(),
                        dependency.getVersion(),
                        dependency.getArtifactId(),
                        dependency.getVersion()
                );
            }
            default -> {
                return String.format(
                        Repository.MAVEN.getLink(),
                        dependency.getGroupId().replace(".", "/"),
                        dependency.getArtifactId(),
                        dependency.getVersion(),
                        dependency.getArtifactId(),
                        dependency.getVersion()
                );
            }
        }
    }
}
