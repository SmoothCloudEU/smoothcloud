package eu.smoothcloud.launcher.dependency;

public class MavenBuilder {
    public static String buildMavenLink(Dependency dependency) {
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
