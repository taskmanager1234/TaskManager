package com.company.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReaderFromFile {

    public static String readAsText(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        byte[] byteArray = Files.readAllBytes(path);
        return new String(byteArray);

    }

    public static byte[] readFromFileAsBytes(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        byte[] bytes = Files.readAllBytes(path);
        String str = new String(bytes);
        return str.getBytes();
    }

}
