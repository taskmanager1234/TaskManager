package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

//todo не удачное название для класса, Writer - это очень общее имя и оно дает очень расплывчатое представление о том, в чем назначение класса
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
        //todo плохая практика делать длинные цепочки вызовов. Такой код сложнее читать и еще сложнее дебажить.
        //нам незачем экономить строки кода, поэтому временные объекты стоит сохранять в переменные локальной области видимости.
    }

    static byte[] readFromFileAsBytes(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName))).getBytes();
    }



}
