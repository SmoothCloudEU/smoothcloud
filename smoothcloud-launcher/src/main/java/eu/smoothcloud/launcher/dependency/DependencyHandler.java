package eu.smoothcloud.launcher.dependency;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DependencyHandler {
    private final Map<String, Path> dependencyPaths;

    public DependencyHandler() {
        this.dependencyPaths = new HashMap<>();
    }

    public String buildUrl(String groupId, String artifactId, String version, String repository, String json) {
        Pattern pattern = Pattern.compile("\"name\": \"" + repository + "\",\\s*\"url\": \"([^\"]+)\"");
        Matcher matcher = Pattern.compile(pattern.pattern()).matcher(json);
        String repoUrl = "";
        if (matcher.find()) {
            repoUrl = matcher.group(1);
        }

        // URL erstellen
        return String.format(repoUrl, groupId.replace('.', '/'), artifactId, version, artifactId, version);
    }

    public Map<String, Path> getDependencyPaths() {
        return dependencyPaths;
    }
}
