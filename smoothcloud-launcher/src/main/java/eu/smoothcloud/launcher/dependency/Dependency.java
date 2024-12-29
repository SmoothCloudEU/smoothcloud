package eu.smoothcloud.launcher.dependency;

public enum Dependency {
    NETTY("io.netty", "netty5-all", "5.0.0.Alpha5"),
    JLINE("org.jline", "jline", "3.28.0"),
    TOMLJ("org.tomlj", "tomlj", "1.1.1"),
    JSON("org.json", "json", "20240303");

    private final String groupId;
    private final String artifactId;
    private final String version;

    Dependency(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }
}
