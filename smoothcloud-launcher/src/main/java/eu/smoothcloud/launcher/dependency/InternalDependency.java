package eu.smoothcloud.launcher.dependency;

public enum InternalDependency {
    NODE("node","https://cdn.smoothcloud.eu/dependencies/node.jar");

    private final String name;
    private final String link;

    InternalDependency(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }
}
