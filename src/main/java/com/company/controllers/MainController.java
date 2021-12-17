package com.company.controllers;

import com.company.constants.ErrorPages;
import com.company.exception.NoSuchTaskException;
import com.company.model.Task;
import com.company.model.JournalStorage;
import com.company.model.TasksJournal;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.UUID;

@Controller
public class MainController {

    @GetMapping(value = "/tasks")
    public String tasks(Model model){
        TasksJournal tasksJournal = JournalStorage.getInstance().getTasksJournal();
        model.addAttribute("tasks", tasksJournal.getTasks()); //в переменную tasks передаем 2 параметр
        return "tasks";
    }

    @GetMapping(value = "/addTask")
    public String addTask(Model model){
        return "addTask";
    }

    @PostMapping("/addTask")
    public String addTaskPost(@RequestParam String title, @RequestParam String description, @RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") Date endDate, Model model){
            System.out.println(endDate);
            Task task = new Task(title, description, endDate);
            JournalStorage.getInstance().getTasksJournal().addTask(task);

        return "redirect:/tasks";
    }


    @GetMapping(value = "/")
    public String getStartPage(Model model){
        return "redirect:/tasks";
    }

    @GetMapping("/updateTask/{id}")
    public String updateTask(@PathVariable String id, Model model) {
        try {
            UUID taskId = UUID.fromString(id);
            Task currentTask = JournalStorage.getInstance().getTasksJournal().getTaskById(taskId);
            model.addAttribute("task", currentTask);
        } catch (NoSuchTaskException e) {
            return ErrorPages.NOT_FOUND;
        }
        return "updateTask";
    }


    @PostMapping("/updateTask/{id}")
    public String updateTaskPost(@PathVariable String id,@RequestParam String title, @RequestParam String description,@RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") Date endDate, Model model){
        try {
            UUID taskId = UUID.fromString(id);
            Task task = new Task(title, description, endDate);
            JournalStorage.getInstance().getTasksJournal().updateTaskByID(taskId, task);
        }catch (NoSuchTaskException e) {
            return ErrorPages.NOT_FOUND;
        }
        return "redirect:/tasks";
    }

}
