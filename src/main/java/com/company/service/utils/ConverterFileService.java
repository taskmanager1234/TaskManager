package com.company.service.utils;

import com.company.constants.ExtensionConstants;
import com.company.exception.SerializationException;
import com.company.exception.UnexpectedFileExtensionException;
import com.company.model.Task;
import com.company.serializer.Serializer;
import com.company.serializer.impl.JsonSerializer;
import com.company.serializer.impl.XmlSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ConverterFileService {


    public List<Task> getTasks(MultipartFile file) throws UnexpectedFileExtensionException, IOException, SerializationException {
        Serializer serializer = getSerializer(file);
        String tasksString = convertFile2String(file);
        return convertString2ListTasks(tasksString, serializer);
    }

    private String convertFile2String(MultipartFile file) throws IOException {
        String tasks = "";
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            tasks = new String(bytes);
        }
        return tasks;
    }

    private List<Task> convertString2ListTasks(String tasks, Serializer serializer) throws SerializationException {

        return serializer.deserializeTasks(tasks);
    }

    private Serializer getSerializer(MultipartFile multipartFile) throws UnexpectedFileExtensionException {
        String extension = multipartFile.getOriginalFilename().split("\\.")[1];
        if (ExtensionConstants.XML.equals(extension)) {
            return new XmlSerializer();
        } else if (ExtensionConstants.JSON.equals(extension)) {
            return new JsonSerializer();
        } else {
            throw new UnexpectedFileExtensionException("Unexpected file type");
        }
    }
}
