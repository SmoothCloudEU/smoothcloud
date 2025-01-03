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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;

public class Downloader {

    public static void downloadJar(String username, String password, String fileUrl, String saveDir, String groupId, String artifactId, String version) throws IOException {
        URL url = new URL(fileUrl);
        String checkDir = "dependencies/" + groupId.replace(".", "/") + "/" + artifactId;
        File checkDirFile = new File(checkDir);
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        if (checkDirFile.exists() && checkDirFile.isDirectory()) {
            File[] files = checkDirFile.listFiles();
            if (files != null && files.length > 0) {
                boolean skip = Arrays.stream(files)
                        .filter(File::isDirectory)
                        .anyMatch(file -> file.getName().equalsIgnoreCase(version));
                if (skip) {
                    System.out.println("Skipped " + artifactId + ".");
                    return;
                }
                boolean versionExists = Arrays.stream(files)
                        .anyMatch(file -> file.isDirectory() && file.getName().equalsIgnoreCase(version));
                if (!versionExists) {
                    Arrays.stream(files).forEach(file -> {
                        if (file.isDirectory()) {
                            deleteRecursively(file);
                        } else {
                            file.delete();
                        }
                    });
                }
                System.out.println(versionExists
                        ? "Updating " + artifactId + "..."
                        : "Downloading " + artifactId + "...");
            }
        }
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            connection.disconnect();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Basic " + encodedAuth);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            responseCode = connection.getResponseCode();
        }
        if (responseCode != HttpURLConnection.HTTP_OK) {
            System.out.println("Argument-Authentifizierung --user --pwd erforderlich.");
            System.exit(1);
            return;
        }
        File saveDirectory = new File(saveDir);
        if (!saveDirectory.exists()) {
            if (!saveDirectory.mkdirs()) {
                System.out.println("Fehler beim Erstellen des Verzeichnisses: " + saveDir);
                return;
            }
        }
        InputStream inputStream = connection.getInputStream();
        File outputFile = new File(saveDir + File.separator + fileName);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outputStream.close();
        System.out.println("Downloaded " + artifactId + ".");
    }

    private static void deleteRecursively(File file) {
        File[] subFiles = file.listFiles();
        if (subFiles != null) {
            for (File subFile : subFiles) {
                if (subFile.isDirectory()) {
                    deleteRecursively(subFile);
                } else {
                    subFile.delete();
                }
            }
        }
        file.delete(); // Lösche das Verzeichnis selbst
    }

}
