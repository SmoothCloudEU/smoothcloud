package eu.smoothcloud.launcher.dependency;

public class MavenCentral {

    public static String buildLink(Dependency dependency) {
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
