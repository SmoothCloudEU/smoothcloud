package eu.smoothcloud.launcher.dependency;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Downloader {

    public static void download(String link, String targetDir, String targetFileName) {
        File downloadDir = new File(targetDir);
        if(!downloadDir.exists())
            downloadDir.mkdirs();
        if(!downloadDir.isDirectory())
            return;
        System.out.println("Begin Download!");
        try (BufferedInputStream input = new BufferedInputStream(new URL(link).openStream())) {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(targetDir, targetFileName));
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            int totalBytes = 0;
            while ((bytesRead = input.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                totalBytes += bytesRead;
            }
            System.out.println("Download Finished! Bytes Written: " + totalBytes / 1024 + " KB");
        } catch (IOException e) {
            System.out.println("Unavailable Link");
        }
    }
}
