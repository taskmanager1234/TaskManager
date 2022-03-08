package com.company.controllers;

import com.company.constants.ErrorPages;
import com.company.constants.PathTemplates;
import com.company.exception.CreateTaskException;
import com.company.exception.DeleteTaskException;
import com.company.model.Task;
import com.company.model.TasksJournal;
import com.company.model.User;
import com.company.service.TaskJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Controller
public class StartController {
    @Autowired
    TaskJournalService taskJournalService;

    private static class PathVariables {
        public static final String JOURNAL_ID = "id";
        public static final String TASK_ID = "taskId";
    }

    @GetMapping(value = "/home")
    public String get(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        UUID id = ((User) (principal)).getId();
        model.addAttribute("journals",taskJournalService.getJournalsByUserId(id));
        return "home";
    }

    @GetMapping(value = "/tasksJournal/addJournal")
    public String showJournalCreateForm(Model model) {
        return "addJournal";
    }

    @PostMapping(value = "/tasksJournal/addJournal")
    public String createJournal(@RequestParam("title") String title, Model model
    ){
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



//    @PostMapping(value = "/home/{journalId}/addJournal")
//    public String createTask(@PathVariable UUID journalId) {
//        TasksJournal task = new TasksJournal(journalId);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = auth.getPrincipal();
//        UUID id = ((User) (principal)).getId();
//        try {
//            task.setTasksJournal(taskJournalService.getById(journalIdReduced));
//            taskJournalService.create(task);
//        } catch (CreateTaskException e) {
//            return ErrorPages.BAD_REQUEST;
//        }
//        //scheduler.scheduleTask(task);
//        return String.format(PathTemplates.REDIRECT_TO_HOME, idJournal);
//    }

}
