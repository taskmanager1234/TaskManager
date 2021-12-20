package com.company;

import com.company.exception.SerializationException;
import com.company.helpers.ReaderFromFile;
import com.company.model.JournalStorage;
import com.company.model.TasksJournal;
import com.company.serializer.impl.JsonSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class StartWebTaskManager {

    public static void main(String[] args) throws SerializationException {
        SpringApplication.run(StartWebTaskManager.class, args);

        //todo а зачем нужна эта инициализация?
        JournalStorage journalStorage = JournalStorage.getInstance(); // инициализация TJ
        try {
            String fileRead = ReaderFromFile.readFromFileAsText("C:\\Users\\denis\\Desktop\\Json\\textJson.json");
            JsonSerializer j = new JsonSerializer();
            try {
                TasksJournal newTaskJournal = j.deserialize(fileRead);
                TasksJournal tasksJournal = journalStorage.getTasksJournal();
                tasksJournal.setTasks(newTaskJournal.getTasks());
           } catch (SerializationException e) {
                throw new SerializationException("Deserialization failed when starting the program! Failed to convert JSON string to Java object",e);
            }
        } catch (IOException e) {
            throw new SerializationException("Unable to load Journal! Could not read from file Json object", e);
       }
    }

}
