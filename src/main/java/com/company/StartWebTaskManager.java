package com.company;

import com.company.exception.SerializationException;
import com.company.model.JournalStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartWebTaskManager {

    public static void main(String[] args) throws SerializationException {

        SpringApplication.run(StartWebTaskManager.class, args);
        //todo а зачем нужна эта инициализация?
        JournalStorage journalStorage = JournalStorage.getInstance(); // инициализация TJ
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
