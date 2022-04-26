package com.company.controllers;

import com.company.constants.AttributeName;
import com.company.constants.ErrorPages;
import com.company.constants.PathTemplates;
import com.company.constants.TaskManagerConstants;
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
        public static final String JOURNALS_CHECKBOX = "journals_checkbox";
        public static final String TITLE = "title";
    }

    @GetMapping(value = TaskManagerConstants.HOME_URL, consumes = MediaType.ALL_VALUE)//TODO
    //consumes - тип принимаемых данных, любой тип может быть принят данным endPoint
    public String getJournalsUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        UUID id = ((User) (principal)).getId();
        model.addAttribute(AttributeName.JOURNALS, taskJournalService.getJournalsByUserId(id));
        return PathTemplates.HOME;
    }

    @GetMapping(value = TaskManagerConstants.ADD_JOURNAL_URL)
    public String showJournalCreateForm() {
        return PathTemplates.CREATE_JOURNAL;
    }

    @PostMapping(value = TaskManagerConstants.ADD_JOURNAL_URL)
    public String createJournal(@RequestParam(PathVariables.TITLE) String title, Model model) {
        TasksJournal tasksJournal = new TasksJournal(title);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        User user = ((User) (principal));
        tasksJournal.setUser(user);
        taskJournalService.create(tasksJournal);
        List<TasksJournal> tasksJournal1 = taskJournalService.getJournalsByUserId(user.getId());
        model.addAttribute(AttributeName.JOURNALS, tasksJournal1);
        return PathTemplates.HOME;
    }


    @PostMapping(TaskManagerConstants.DELETE_JOURNAL_URL)
    public String deleteJournals(@RequestParam(PathVariables.JOURNALS_CHECKBOX) String[] ids) {

        for (String currentId : ids) {
            try {
                taskJournalService.deleteJournalById(UUID.fromString(currentId));
            } catch (DeleteTaskException e) {
                return ErrorPages.NOT_FOUND;
            }
        }
        return PathTemplates.REDIRECT_HOME;
    }


}
