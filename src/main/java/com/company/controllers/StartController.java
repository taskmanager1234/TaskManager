package com.company.controllers;

import com.company.constants.ErrorPages;
import com.company.exception.DeleteTaskException;
import com.company.model.TasksJournal;
import com.company.model.User;
import com.company.service.TaskJournalService;
import com.company.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Controller
public class StartController {
    @Autowired
    TaskJournalService taskJournalService;

    @Autowired
    TaskService taskService;

    private static class PathVariables {
        public static final String JOURNAL_ID = "id";
        public static final String TASK_ID = "taskId";
    }

    //todo: согласованные имена
    @GetMapping(value = "/home" , consumes = MediaType.ALL_VALUE)
    public String get(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        UUID id = ((User) (principal)).getId();
        model.addAttribute("journals", taskJournalService.getJournalsByUserId(id));
        return "home";
    }

    @GetMapping(value = "/tasksJournal/addJournal")
    public String showJournalCreateForm(Model model) {
        return "addJournal";
    }

    @PostMapping(value = "/tasksJournal/addJournal")
    public String createJournal(@RequestParam("title") String title, Model model
    ) {
        TasksJournal tasksJournal = new TasksJournal(title);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        User user = ((User) (principal));
        tasksJournal.setUser(user);
        taskJournalService.create(tasksJournal);
        List<TasksJournal> tasksJournal1 = taskJournalService.getJournalsByUserId(user.getId());
        model.addAttribute("journals", tasksJournal1);
        return "home";
    }

    //todo: переделать но новые рельсы
    @PostMapping("/tasksJournal/deleteJournal")
    public String deleteTasks(@RequestBody String ids,
                              Model model) {
        String[] stringIds = ids.split(",");

        for (String currentId : stringIds) {
            try {
                taskJournalService.deleteJournalById(UUID.fromString(currentId));
            } catch (DeleteTaskException e) {
                return ErrorPages.NOT_FOUND;
            }
        }
        return "redirect:/home";
    }


}
