package com.company.helpers;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//todo не удачное название для класса, Writer - это очень общее имя и оно дает очень расплывчатое представление о том, в чем назначение класса
public class Writer {

    public static void writeToFileAsText(String o, String fileName) throws IOException {
       FileWriter fileWriter = new FileWriter(fileName);
       fileWriter.append(o);
       fileWriter.close();
   }


    public static void writeToFileAsBytes(byte[] o, String fileName) throws IOException {
        File file = new File(fileName);
        Path path = file.toPath();
        Files.write(path, o);
    }




}
