package com.company;

import com.company.exception.SerializationException;
import com.company.helpers.ReaderFromFile;
import com.company.model.TasksJournal;
import com.company.serializer.impl.JsonSerializer;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class JournalLoader {
    @Value( "${task_manager.in_memory.filepath}")
    private static String filePath; //"C:/Users/denis/Desktop/Json/textJson.json";

    public static TasksJournal getFromFile() throws SerializationException {
        try {
            String fileRead = ReaderFromFile.readAsText(filePath);
            JsonSerializer jsonSerializer = new JsonSerializer();
            try {
                return jsonSerializer.deserialize(fileRead);
            } catch (SerializationException e) {
                throw new SerializationException("Deserialization failed when starting the program! Failed to convert JSON string to Java object",e);
            }
        } catch (IOException e) {
            throw new SerializationException("Unable to load Journal! Could not read from file Json object", e);
        }
    }
}
