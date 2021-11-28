package com.company;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Task task = new Task("Weather", "The Sun is shining", new Date(), new Date());
        Task task2 = new Task("ArrayList", "ArrayList.add", new Date(), new Date());
        System.out.println(task);
        task.setDescription("Sun");


        TasksJournal tasksJournal = new TasksJournal();
        tasksJournal.addTask(task);
        tasksJournal.addTask(task2);
        System.out.println(tasksJournal.toString());


        System.out.println("");
        JsonSerializer j = new JsonSerializer();
        Object o = j.serialize(tasksJournal);
        //System.out.println(j.deserialize(o));


       // FileWriter out = new FileWriter("C:\\Users\\denis\\Desktop\\Json\\textJson.json");
        Writer.writeToFileAsText((String)o, "C:\\Users\\denis\\Desktop\\Json\\textJson.json");

        String d = Writer.readFromFileAsText("C:\\Users\\denis\\Desktop\\Json\\textJson.json");
       // System.out.println(d);
        System.out.println (j.deserialize(d));

        Writer.writeToFileAsBytes(((String) o).getBytes(), "C:\\Users\\denis\\Desktop\\Json\\textByte.bin");

        String readBytes = new  String(Writer.readFromFileAsBytes("C:\\Users\\denis\\Desktop\\Json\\textByte.bin"));
        System.out.println (j.deserialize(readBytes));





    }
}
