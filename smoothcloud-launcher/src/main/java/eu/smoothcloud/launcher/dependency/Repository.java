package eu.smoothcloud.launcher.dependency;

public enum Repository {
    MAVEN("https://repo1.maven.org/maven2/%s/%s/%s/%s-%s.jar"),
    SMOOTHCLOUD_CDN("https://cdn.smoothcloud.eu/%s/%s/%s/%s-%s.jar");

    private final String link;

    Repository(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
