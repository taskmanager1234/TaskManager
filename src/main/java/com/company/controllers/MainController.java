package com.company.controllers;

import com.company.model.Task;
import com.company.model.TaskManagerSingleton;
import com.company.model.TasksJournal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Controller
public class MainController {

    @GetMapping(value = "/test")
    public String testGet(Model model){
        TasksJournal tasksJournal = TaskManagerSingleton.getInstance().getTasksJournal();
        model.addAttribute("tasks", tasksJournal.getTasks()); //в переменную tasks передаем 2 параметр
        return "test";
    }

    @GetMapping(value = "/addTask")
    public String addTask(Model model){
        return "addTask";
    }

    @PostMapping("/addTask")
    public String addTaskPost(@RequestParam String title, @RequestParam String description,@RequestParam String endDate, Model model){
        try {
            System.out.println(endDate);
            Date date = convertDate(endDate);
            Task task = new Task(title, description, date);
            TaskManagerSingleton.getInstance().addTask(task);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "redirect:/test";
    }

    public static Date convertDate(String date) throws ParseException {
         date = date.replace('T', ' ');
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date docDate= format.parse(date);
     return docDate;
    }


    @GetMapping(value = "/")
    public String getStartPage(Model model){
        return "redirect:/test";
    }

    @GetMapping("/updateTask/{id}")
    public String updateTask(@PathVariable String id, Model model) {
        try {
            UUID taskId = UUID.fromString(id);
            Task currentTask = TaskManagerSingleton.getInstance().getTasksJournal().getTaskById(taskId);
            model.addAttribute("task", currentTask);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "updateTask";
    }


    @PostMapping("/updateTask/{id}")
    public String updateTaskPost(@PathVariable String id,@RequestParam String title, @RequestParam String description,@RequestParam String endDate, Model model){
        try {
            UUID taskId = UUID.fromString(id);
            Date date = convertDate(endDate);
            Task task = new Task(title, description, date);
            TaskManagerSingleton.getInstance().updateTaskById(taskId, task);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/test";
    }

}
