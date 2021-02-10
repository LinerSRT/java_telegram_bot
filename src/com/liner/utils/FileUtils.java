package com.liner.utils;

import com.sun.istack.internal.Nullable;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class FileUtils {


    public static void writeFile(String content, File file) {
        if (file.exists())
            if (!file.delete()) {
                System.out.println("[ERROR] Cant delete original file {" + file.getAbsolutePath() + "}");
            }
        try {
            if (!file.createNewFile()) {
                System.out.println("[ERROR] Cant create file {" + file.getAbsolutePath() + "}");
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(content.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public static String readFile(File file) {
        try {
            StringBuilder stringBuffer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
            char[] buffer = new char[1024];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                stringBuffer.append(String.valueOf(buffer, 0, read));
            }
            reader.close();
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean containFile(File directory, String[] files) {
        if (directory.isFile())
            return false;
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            for (String names : files) {
                if (file.getName().contains(names)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void downloadFile(String fileUrl, File destinationFile) throws IOException {
        URL website = new URL(fileUrl);
        URLConnection connection = website.openConnection();
        try (InputStream in = connection.getInputStream()) {
            Files.copy(in, destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
