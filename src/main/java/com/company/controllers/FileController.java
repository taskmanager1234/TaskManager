package com.company.controllers;

import com.company.exception.SerializationException;
import com.company.helpers.WriterToFile;
import com.company.model.TaskManagerSingleton;
import com.company.model.TasksJournal;
import com.company.serializer.impl.JsonSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class FileController {


    @GetMapping(value = "/save")
    public String saveTaskJournalInFile(Model model){
        TasksJournal tasksJournal = TaskManagerSingleton.getInstance().getTasksJournal();
        JsonSerializer jsonSerializer = new JsonSerializer();
        Object o = null;
        try {
             o = jsonSerializer.serialize(tasksJournal);
        } catch (SerializationException e) {
            e.printStackTrace();
        }
        try {
            WriterToFile.writeToFileAsText((String)o, "C:\\Users\\denis\\Desktop\\Json\\textJson.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/test";
    }
}
