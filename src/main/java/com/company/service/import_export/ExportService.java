package com.company.service.import_export;

import com.company.constants.ExtensionConstants;
import com.company.exception.SerializationException;
import com.company.exception.UnexpectedFileExtensionException;
import com.company.model.Task;
import com.company.serializer.impl.JsonSerializer;
import com.company.serializer.impl.XmlSerializer;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@Service
public class ExportService {


//TODO rename format
    public ResponseEntity<InputStreamResource> exportTasks(List<Task> tasks, String extension) throws UnexpectedFileExtensionException, SerializationException {

        HttpHeaders header = new HttpHeaders();
        if(ExtensionConstants.JSON.equals(extension)) {
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tasks.json");
            header.setContentType(MediaType.APPLICATION_JSON);

        }
        if(ExtensionConstants.XML.equals(extension)){
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tasks.xml");
            header.setContentType(MediaType.APPLICATION_XML);
        }

        String tasksStr = tasks2String(tasks, extension); //TODO tasksTo
        byte[] tasksBytes = tasksStr.getBytes();
        InputStream resource = new ByteArrayInputStream(tasksBytes);

        InputStreamResource fileForDownload = new InputStreamResource(resource);

        return ResponseEntity.ok()
                .headers(header)
                .body(fileForDownload);
    }

//TODO if-else заменить на switch
    //TODO Serializer
    //TODO метод для резолва (выбирает) Serializer
    public String tasks2String(List<Task> tasks, String extension) throws SerializationException, UnexpectedFileExtensionException {
        JsonSerializer jsonSerializer = new JsonSerializer();
        XmlSerializer xmlSerializer = new XmlSerializer(); //TODO singleton
        if (ExtensionConstants.XML.equals(extension)) {
            return (String) xmlSerializer.serializeTasks(tasks);
        } else if (ExtensionConstants.JSON.equals(extension)) {
            return (String) jsonSerializer.serializeTasks(tasks);
        }
        throw new UnexpectedFileExtensionException("Failed to convert to string");

    }


}
