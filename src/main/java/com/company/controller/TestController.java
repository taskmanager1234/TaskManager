package com.company.controller;

import com.company.model.Task;
import com.company.model.TasksJournal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
public class TestController {

    @GetMapping(value = "/test")
    public String testGet(Model model){
        Task task = new Task("Weather", "The Sun is shining", new Date(), new Date());
        Task task2 = new Task("ArrayList", "ArrayList.add", new Date(), new Date());
        TasksJournal tasksJournal = new TasksJournal();
        tasksJournal.addTask(task);
        tasksJournal.addTask(task2);
        model.addAttribute("tasks", tasksJournal.getTasks());

        return "test";
    }


}
