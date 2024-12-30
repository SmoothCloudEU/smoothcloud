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
            System.setProperty("startup", String.valueOf(System.currentTimeMillis()));
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
