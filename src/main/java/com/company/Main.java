package com.company;

import com.company.exception.SerializationException;
import com.company.helpers.ReadingFromFile;
import com.company.helpers.WritingToFile;
import com.company.model.Task;
import com.company.model.TasksJournal;
import com.company.serializer.impl.JsonSerializer;

import java.io.IOException;
import java.util.Date;

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
        Object o = null;
        try {
            o = j.serialize(tasksJournal);
        } catch (SerializationException e) {
            e.printStackTrace();
        }
        //System.out.println(j.deserialize(o));


       // FileWriter out = new FileWriter("C:\\Users\\denis\\Desktop\\Json\\textJson.json");
        WritingToFile.writeToFileAsText((String)o, "C:\\Users\\denis\\Desktop\\Json\\textJson.json");

        String d = ReadingFromFile.readFromFileAsText("C:\\Users\\denis\\Desktop\\Json\\textJson.json");
       // System.out.println(d);
        System.out.println("Deserialize");
        try {
            System.out.println (j.deserialize(d));
        } catch (SerializationException e) {
            e.printStackTrace();
        }

        String str = new String("Des");
        WritingToFile.writeToFileAsBytes((str).getBytes(), "C:\\Users\\denis\\Desktop\\Json\\textByte1.bin");

        String readBytes = new  String(ReadingFromFile.readFromFileAsBytes("C:\\Users\\denis\\Desktop\\Json\\textByte.bin"));
        try {
            System.out.println (j.deserialize(readBytes));
        } catch (SerializationException e) {
            e.printStackTrace();
        }


//        YamlSerializer yamlSerializer = new YamlSerializer();
//        Object serializeYaml = yamlSerializer.serialize(tasksJournal);
//        Writer.writeToFileAsText((String)serializeYaml , "C:\\Users\\denis\\Desktop\\Json\\yaml.yaml");
//
//        BytesSerializer bytesSerializer = new BytesSerializer();
//        Object bytes = bytesSerializer.serialize(tasksJournal);
//        Writer.writeToFileAsBytes((byte[]) bytes , "C:\\Users\\denis\\Desktop\\Json\\bytes2.bin");
//
//
//        XmlSerializer xmlSerializer = new XmlSerializer();
//        Object xml = xmlSerializer.serialize(tasksJournal);
//        Writer.writeToFileAsText((String) xml , "C:\\Users\\denis\\Desktop\\Json\\xml.xml");





    }
}
