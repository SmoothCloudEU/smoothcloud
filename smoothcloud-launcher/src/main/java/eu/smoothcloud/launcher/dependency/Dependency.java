package eu.smoothcloud.launcher.dependency;

public enum Dependency {
    NETTY("io.netty", "netty5-all", "5.0.0.Alpha5", Repository.MAVEN),
    JLINE("org.jline", "jline", "3.28.0", Repository.MAVEN),
    TOMLJ("org.tomlj", "tomlj", "1.1.1", Repository.MAVEN),
    JSON("org.json", "json", "20240303", Repository.MAVEN),
    ANTLR("org.antlr", "antlr4-runtime", "4.11.1", Repository.MAVEN),
    SMOOTHCLOUD_NODE("eu.smoothcloud", "smoothcloud-node", "1.0.0-dev", Repository.SMOOTHCLOUD_CDN);

    private final String groupId;
    private final String artifactId;
    private final String version;
    private final Repository repository;

    Dependency(String groupId, String artifactId, String version, Repository repository) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.repository = repository;
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

    public Repository getRepository() {
        return repository;
    }
}
