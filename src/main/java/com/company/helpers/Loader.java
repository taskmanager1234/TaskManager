package com.company.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Loader {
    // разбить на строчки
    public static String readFromFileAsText(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        byte[] byteArray = Files.readAllBytes(path);
        return new String(byteArray);
        //todo плохая практика делать длинные цепочки вызовов. Такой код сложнее читать и еще сложнее дебажить.
        //нам незачем экономить строки кода, поэтому временные объекты стоит сохранять в переменные локальной области видимости.
    }

    public static byte[] readFromFileAsBytes(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        byte[] bytes = Files.readAllBytes(path);
        String str = new String(bytes);
        return str.getBytes();
    }

}
