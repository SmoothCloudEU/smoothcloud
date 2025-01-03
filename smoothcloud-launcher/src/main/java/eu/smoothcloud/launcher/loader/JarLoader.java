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

import eu.smoothcloud.launcher.SmoothCloudClassLoader;
import eu.smoothcloud.launcher.SmoothCloudLauncher;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

public class JarLoader {

    public void loadJar(Path jarPath, String[] args) {
        try (SmoothCloudClassLoader classLoader = SmoothCloudLauncher.getClassLoader()) {
            classLoader.addURL(jarPath.toUri().toURL());
            System.setProperty("smoothcloud-startup", String.valueOf(System.currentTimeMillis()));
            Thread.currentThread().setContextClassLoader(classLoader);
            String mainClass = mainClass(jarPath);
            Class<?> clazz = Class.forName(mainClass, true, classLoader);
            clazz.getMethod("main", String[].class).invoke(null, new Object[]{args});
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IOException | ClassNotFoundException | IllegalAccessException |
                 NoSuchMethodException e) {
            System.err.println("General error during JAR loading: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String mainClass(Path jarPath) {
        try (JarFile jarFile = new JarFile(jarPath.toFile())) {
            var manifest = jarFile.getManifest();
            if (manifest != null) {
                var mainAttributes = manifest.getMainAttributes();
                String mainClass = mainAttributes.getValue(Attributes.Name.MAIN_CLASS);
                if (mainClass != null) {
                    return mainClass;
                } else {
                    throw new RuntimeException("No main class detectable in the manifest!");
                }
            } else {
                throw new RuntimeException("Manifest is missing in the jar file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to determine main class", e);
        }
    }
}
