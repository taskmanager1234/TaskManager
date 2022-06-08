package com.company.service.utils;

import com.company.constants.ExtensionConstants;
import com.company.exception.SerializationException;
import com.company.exception.UnexpectedFileExtensionException;
import com.company.model.Task;
import com.company.model.TasksJournal;
import com.company.serializer.JournalDto;
import com.company.serializer.Serializer;
import com.company.serializer.impl.JsonSerializer;
import com.company.serializer.impl.XmlSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ConverterFileService {


    public List<Task> getTasks(MultipartFile file) throws UnexpectedFileExtensionException, IOException, SerializationException {
        Serializer<JournalDto> serializer = getSerializer(file);

        String tasksString = convertFileToString(file);
        JournalDto journalDto = convertStringToJournalDto(tasksString, serializer);
        return getListTasksFromDto(journalDto);
    }




    private String convertFileToString(MultipartFile file) throws IOException {
        String tasks = "";
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            tasks = new String(bytes);
        }
        return tasks;
    }

//    private List<Task> convertStringToListTasks(String tasks, Serializer serializer) throws SerializationException {
//
//        return (List<Task>) serializer.deserialize(tasks);
//    }

    private JournalDto convertStringToJournalDto(String tasks, Serializer serializer) throws SerializationException, JsonProcessingException {

        return (JournalDto) serializer.deserialize(tasks, JournalDto.class);
    }


    public List<Task> getListTasksFromDto(JournalDto journalDto){
        List<Task> tasks = journalDto.getTasks();
        TasksJournal tasksJournal= new TasksJournal();
        tasksJournal.setJournalName(journalDto.getJournalName());
        tasksJournal.setId(UUID.fromString(journalDto.getId()));
        for(Task task:tasks){
            task.setTaskJournal(tasksJournal);
        }
        return tasks;
    }

    private Serializer<JournalDto> getSerializer(MultipartFile multipartFile) throws UnexpectedFileExtensionException {
        String extension = multipartFile.getOriginalFilename().split("\\.")[1];
        if (ExtensionConstants.XML.equals(extension)) {
            return new XmlSerializer<>();
        } else if (ExtensionConstants.JSON.equals(extension)) {
            return new JsonSerializer<>();
        } else {
            throw new UnexpectedFileExtensionException("Unexpected file type");
        }
    }
}
