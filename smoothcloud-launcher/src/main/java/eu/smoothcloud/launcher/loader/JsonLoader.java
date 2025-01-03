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

package eu.smoothcloud.launcher.loader;

import eu.smoothcloud.launcher.SmoothCloudLauncher;
import eu.smoothcloud.launcher.dependency.DependencyHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonLoader {
    private final String json;

    public JsonLoader(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        this.json = response.toString();
    }

    public void processJsonAndDownload(String username, String password) {
        DependencyHandler dependencyHandler = SmoothCloudLauncher.getDependencyHandler();
        Pattern pattern = Pattern.compile("\"groupId\": \"([^\"]+)\",\\s*\"artifactId\": \"([^\"]+)\",\\s*\"version\": \"([^\"]+)\",\\s*\"repository\": \"([^\"]+)\"");
        Matcher matcher = pattern.matcher(this.json);
        while (matcher.find()) {
            String groupId = matcher.group(1);
            String artifactId = matcher.group(2);
            String version = matcher.group(3);
            String repository = matcher.group(4);
            String url = dependencyHandler.buildUrl(groupId, artifactId, version, repository, this.json);
            String saveDir = "dependencies/" + groupId.replace(".", "/") + "/" + artifactId + "/" + version + "/";
            Path path = Path.of(saveDir + artifactId + "-" + version + ".jar");
            try {
                Downloader.downloadJar(username, password, url, saveDir, groupId, artifactId, version);
                dependencyHandler.getDependencyPaths().put(artifactId, path);
                SmoothCloudLauncher.getClassLoader().addURL(path.toUri().toURL());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
