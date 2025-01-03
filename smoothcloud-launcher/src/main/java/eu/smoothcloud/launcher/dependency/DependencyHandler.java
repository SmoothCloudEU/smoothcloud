/*
 * Copyright (c) 2025 SmoothCloud team & contributors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        return String.format(repoUrl, groupId.replace('.', '/'), artifactId, version, artifactId, version);
    }

    public Map<String, Path> getDependencyPaths() {
        return dependencyPaths;
    }
}
