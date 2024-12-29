package eu.smoothcloud.launcher;

import eu.smoothcloud.launcher.dependency.DependencyLoader;
import eu.smoothcloud.launcher.dependency.InternalDependencyLoader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

public class SmoothCloudLauncher {

    public static void main(String[] args) {
        DependencyLoader.loadExternalDependencys();
        InternalDependencyLoader.loadInternalDependencys();
        System.out.println("Start Node Module!");
        try (SmoothCloudClassLoader classLoader = new SmoothCloudClassLoader()) {
            classLoader.addURL(bootFile().toUri().toURL());
            System.setProperty("startup", String.valueOf(System.currentTimeMillis()));
            Thread.currentThread().setContextClassLoader(classLoader);
            Class.forName(mainClass(), true, classLoader).getMethod("main", String[].class).invoke(null, new Object[]{args});
        } catch (IOException | ClassNotFoundException | InvocationTargetException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path bootFile() {
        return Path.of("dependencies/eu.smoothcloud/smoothcloud-node-1.0.0-dev.jar");
    }

    private static String mainClass() {
        try (JarFile jarFile = new JarFile(bootFile().toFile())) {
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
