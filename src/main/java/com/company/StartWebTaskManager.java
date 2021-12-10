package com.company;

import java.io.IOException;
import java.util.Arrays;

import com.company.exception.SerializationException;
import com.company.helpers.ReadingFromFile;
import com.company.model.TasksJournal;
import com.company.serializer.impl.JsonSerializer;
import com.fasterxml.jackson.databind.JsonSerializable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StartWebTaskManager {

    public static void main(String[] args) throws SerializationException {

        SpringApplication.run(StartWebTaskManager.class, args);
        try {
            String d = ReadingFromFile.readFromFileAsText("C:\\Users\\denis\\Desktop\\Json\\textJson.json");
            JsonSerializer j = new JsonSerializer();
            TasksJournal tasksJournal = null;
            try {
                tasksJournal = j.deserialize(d);
            } catch (SerializationException e) {
                throw new SerializationException("Deserialization failed when starting the program! Failed to convert JSON string to Java object",e);
            }
            JournalContext.setTasksJournal(tasksJournal);
        } catch (IOException e) {
            throw new SerializationException("Unable to load Journal! Could not read from file Json object", e);
        }
    }

}
