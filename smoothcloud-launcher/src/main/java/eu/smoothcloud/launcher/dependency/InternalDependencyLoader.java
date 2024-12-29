package eu.smoothcloud.launcher.dependency;

import java.io.File;
import java.util.Objects;

public class InternalDependencyLoader {
    private static final String MAIN_FOLDER = "dependencies/";
    private static final String JAR_EXTENSION = ".jar";

    public static void loadInternalDependencys() {
        DependencyUtil.getIntDepByManifest().forEach((groupId, pair) -> {
            String url = "https://cdn.smoothcloud.eu/dependencies/" + pair.getKey() + "-" + pair.getValue() + ".jar";

            String inDepDir = MAIN_FOLDER + groupId;
            String inDepFileName = pair.getKey() + "-" + pair.getValue() + JAR_EXTENSION;
            if (!new File(inDepDir, inDepFileName).exists()) {
                File tempFile = new File(inDepDir);
                if (tempFile.exists()) {
                    for (File file : Objects.requireNonNull(tempFile.listFiles())) {
                        file.delete();
                    }
                }
                Downloader.download(
                        url,
                        inDepDir,
                        inDepFileName
                );
            }
            System.out.println("Skipped " + inDepFileName);
        });
    }
}
