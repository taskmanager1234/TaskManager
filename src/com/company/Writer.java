package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Writer {

   static void writeToFileAsText(String o, String fileName) throws IOException {
       FileWriter fileWriter = new FileWriter(fileName);
       fileWriter.append(o);
       fileWriter.close();
   }


    static void writeToFileAsBytes(byte[] o, String fileName) throws IOException {
        FileOutputStream fileWriter = new FileOutputStream(fileName);
        fileWriter.write(o);
        fileWriter.close();
    }


    static String readFromFileAsText(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    static byte[] readFromFileAsBytes(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName))).getBytes();
    }



}
