package com.company;

import java.io.IOException;
import java.util.Date;

import com.company.exception.SerializationException;
import com.company.helpers.ReaderFromFile;
import com.company.model.Task;
import com.company.model.TaskManagerSingleton;
import com.company.model.TasksJournal;
import com.company.serializer.impl.JsonSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartWebTaskManager {

    public static void main(String[] args) throws SerializationException {

        SpringApplication.run(StartWebTaskManager.class, args);
        TaskManagerSingleton taskManagerSingleton = TaskManagerSingleton.getInstance(); // инициализация TJ
//        try {
//            String d = ReaderFromFile.readFromFileAsText("C:\\Users\\denis\\Desktop\\Json\\textJson.json");
//            JsonSerializer j = new JsonSerializer();
//            try {
//                taskManagerSingleton.setTasksJournal(j.deserialize(d));
//           } catch (SerializationException e) {
//                throw new SerializationException("Deserialization failed when starting the program! Failed to convert JSON string to Java object",e);
//            }
//        } catch (IOException e) {
//            throw new SerializationException("Unable to load Journal! Could not read from file Json object", e);
//        }
    }

}
