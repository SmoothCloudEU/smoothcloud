package eu.smoothcloud.launcher.loader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Scanner;

public class Downloader {

    public static void downloadJar(String fileUrl, String saveDir, String groupId, String artifactId, String version) throws IOException {
        URL url = new URL(fileUrl);
        String checkDir = "dependencies/" + groupId.replace(".", "/") + "/" + artifactId;
        File checkDirFile = new File(checkDir);
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        if (checkDirFile.exists()) {
            if (checkDirFile.isDirectory()) {
                if (checkDirFile.listFiles() != null && checkDirFile.listFiles().length > 0) {
                    boolean skip = false;
                    for (File file : checkDirFile.listFiles()) {
                        if (file.isDirectory()) {
                            if (file.getName().equalsIgnoreCase(version)) {
                                skip = true;
                                break;
                            }
                            return;
                        }
                    }
                    if (skip) {
                        System.out.println("Skipped " + artifactId + ".");
                        return;
                    }
                    System.out.println("Downloading " + artifactId + "...");
                }
            }
        }
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            System.out.println("Authentifizierung erforderlich.");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Benutzername: ");
            String username = scanner.nextLine();
            System.out.print("Passwort: ");
            String password = scanner.nextLine();
            scanner.close();
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
            System.out.println("Kein erfolgreicher HTTP-Code: " + responseCode);
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
}
