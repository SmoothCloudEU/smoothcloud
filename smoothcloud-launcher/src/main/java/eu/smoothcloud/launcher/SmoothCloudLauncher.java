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

package eu.smoothcloud.launcher;

import eu.smoothcloud.launcher.dependency.DependencyHandler;
import eu.smoothcloud.launcher.loader.JarLoader;
import eu.smoothcloud.launcher.loader.JsonLoader;

import java.io.IOException;

public class SmoothCloudLauncher {
    private static SmoothCloudClassLoader classLoader;
    private static DependencyHandler dependencyHandler;
    private static JsonLoader jsonLoader;
    private static JarLoader jarLoader;

    public static void main(String[] args) throws IOException {
        String username = null;
        String password = null;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--user" -> {
                    if (i + 1 < args.length) {
                        username = args[i + 1];
                        i++;
                    } else {
                        System.out.println("Fehler: Kein Benutzername angegeben.");
                        return;
                    }
                }
                case "--pwd" -> {
                    if (i + 1 < args.length) {
                        password = args[i + 1];
                        i++;
                    } else {
                        System.out.println("Fehler: Kein Passwort angegeben.");
                        return;
                    }
                }
            }
        }
        classLoader = new SmoothCloudClassLoader();
        dependencyHandler = new DependencyHandler();
        jsonLoader = new JsonLoader("https://github.com/SmoothCloudEU/smoothcloud-manifest/raw/refs/heads/master/dependencyLoader.json");
        jsonLoader.processJsonAndDownload(username, password);
        System.out.println("Starting smoothcloud-node...");
        jarLoader = new JarLoader();
        jarLoader.loadJar(dependencyHandler.getDependencyPaths().get("smoothcloud-node"), new String[]{});
    }

    public static SmoothCloudClassLoader getClassLoader() {
        return classLoader;
    }

    public static DependencyHandler getDependencyHandler() {
        return dependencyHandler;
    }

    public static JsonLoader getJsonLoader() {
        return jsonLoader;
    }

    public static JarLoader getJarLoader() {
        return jarLoader;
    }
}
