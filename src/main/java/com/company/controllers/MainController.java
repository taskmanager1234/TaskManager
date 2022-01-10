package com.company.controllers;

import com.company.constants.Endpoints;
import com.company.constants.ErrorPages;
import com.company.constants.PathTemplates;
import com.company.exception.NoSuchTaskException;
import com.company.model.Task;
import com.company.model.TasksJournal;
import com.company.repository.TaskRepository;
import com.company.scheduler.CancellableScheduler;
import com.company.service.TaskJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.UUID;
//ctrl+alt+o
@Controller
public class MainController {
    private static final String MODEL_ATTRIBUTE_TASK = "tasks";
    @Autowired
    private CancellableScheduler scheduler;

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskJournalService taskJournalService;

    @GetMapping(value = Endpoints.TASKS)
    public String tasks(Model model) {
        TasksJournal tasksJournal = taskJournalService.getTasksJournal();
        model.addAttribute(MODEL_ATTRIBUTE_TASK, tasksJournal.getTasks()); //в переменную tasks передаем 2 параметр
        return PathTemplates.TASKS;
    }

    @GetMapping(value = Endpoints.ADD_TASK)
    public String addTask() {
        return PathTemplates.ADD_TASK;
    }

    @PostMapping(value = Endpoints.ADD_TASK)
    public String addTaskPost(@RequestParam String title,
                              @RequestParam String description,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        System.out.println(endDate);
        if(endDate.isBefore(startDate))
            throw new RuntimeException("End date must be after start date");
        Task task = new Task(title, description, startDate, endDate);
     //   JournalStorage.getInstance().getTasksJournal().addTask(task);
        taskRepository.save(task);
        scheduler.scheduleTask(task);
        return PathTemplates.REDIRECT_TO_HOME;
    }


    @GetMapping(value = Endpoints.MAIN_PAGE)
    public String getStartPage() {
        return PathTemplates.REDIRECT_TO_HOME;
    }

    @GetMapping(Endpoints.UPDATE_TASK)
    public String updateTask(@PathVariable String id, Model model) {
        try {
            UUID taskId = UUID.fromString(id);
            if(taskRepository.findById(taskId).isPresent()) {
                Task currentTask = taskRepository.findById(taskId).get();
                model.addAttribute(MODEL_ATTRIBUTE_TASK, currentTask);
            }
            else {
                throw new NoSuchTaskException("Task with id ="+ id +" not found");
            }
        } catch (NoSuchTaskException e) {
            return ErrorPages.NOT_FOUND;
        }
        return PathTemplates.UPDATE_TASK;
    }


    @PostMapping(Endpoints.UPDATE_TASK)
    public String updateTaskPost(@PathVariable String id,
                                 @RequestParam String title,
                                 @RequestParam String description,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {

            UUID taskId = UUID.fromString(id);
            Task task = new Task(taskId, title, description, startDate, endDate);
           // JournalStorage.getInstance().getTasksJournal().updateTaskByID(taskId, task);
            taskRepository.save(task);

        return PathTemplates.REDIRECT_TO_HOME;
    }


    @PostMapping(Endpoints.DELETE_TASK)
    public String deleteTaskPost(@PathVariable String id) {
        UUID taskId = UUID.fromString(id);
       // JournalStorage.getInstance().getTasksJournal().removeTask(taskId);
        taskRepository.deleteById(taskId);
        return PathTemplates.REDIRECT_TO_HOME;
    }

}
