package com.company.controllers;

import com.company.constants.AttributeName;
import com.company.constants.ErrorPages;
import com.company.constants.PathTemplates;
import com.company.constants.TaskManagerConstants;
import com.company.exception.*;
import com.company.model.TasksJournal;
import com.company.model.User;
import com.company.service.AuthenticationService;
import com.company.service.TaskJournalService;
import com.company.service.TaskService;
import com.company.service.import_export.ExportService;
import com.company.service.import_export.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Controller
public class StartController {


    private final TaskJournalService taskJournalService;
    private final TaskService taskService;
    private final ExportService exportService;
    private final ImportService importService;
    private final AuthenticationService authenticationService;
    //private final ImportValidator importValidator;

    @Autowired
    public StartController(TaskJournalService taskJournalService,
                           TaskService taskService,
                           ExportService exportService,
                           ImportService importService,
                           AuthenticationService authenticationService
//                           ImportValidator importValidator
                           ) {
        this.taskJournalService = taskJournalService;
        this.taskService = taskService;
        this.exportService = exportService;
        this.importService = importService;
        this.authenticationService = authenticationService;
       // this.importValidator = importValidator;
    }

    private static class PathVariables {
        public static final String JOURNALS_CHECKBOX = "journals_checkbox";
        public static final String TITLE = "title";
        public static final String FILE = "file";
    }

    @GetMapping(value = TaskManagerConstants.JOURNALS_URL, consumes = MediaType.ALL_VALUE) //TODO JOURNALS_URL
    //consumes - тип принимаемых данных, любой тип может быть принят данным endPoint
    public String getJournalsUser(Model model) {
        User currentUser = authenticationService.getCurrentUser();
        model.addAttribute(AttributeName.JOURNALS,
                taskJournalService.getJournalsByUserId(currentUser.getId()));
        return PathTemplates.HOME;
    }

    @GetMapping(value = TaskManagerConstants.JOURNAL_CREATE_FORM_URL)//TODO JOURNAL_CREATE_FORM_URL
    public String showJournalCreateForm() {
        return PathTemplates.CREATE_JOURNAL;
    }//TODO

    @PostMapping(value = TaskManagerConstants.JOURNAL_CREATE_FORM_URL) //TODO
    public String createJournal(@RequestParam(PathVariables.TITLE) String title, Model model) {
        TasksJournal tasksJournal = new TasksJournal(title);

        User currentUser = authenticationService.getCurrentUser();
        tasksJournal.setUser(currentUser);
        taskJournalService.create(tasksJournal);
        List<TasksJournal> tasksJournal1 = taskJournalService.getJournalsByUserId(currentUser.getId());
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


    @GetMapping(value = TaskManagerConstants.IMPORT_URL)
    public String getImportTasks() {

        return PathTemplates.IMPORT;
    }

    @PostMapping(value = TaskManagerConstants.IMPORT_URL)
    public String importTasks(@RequestParam(name = PathVariables.FILE) MultipartFile tasks,
                              @RequestParam String mode, Model model
            /*@RequestParam Boolean report*/) throws SerializationException, BadImportException, CreateTaskException, IOException, UnexpectedFileExtensionException, TaskNotFoundException {
//        if (!importValidator.validate(tasks).isEmpty())
//            try {
//                throw new ValidationException(importValidator.validate(tasks).getAllErrorsAsString());
//            } catch (ValidationException e) {
//                model.addAttribute(AttributeName.ERROR_MESSAGE, e.getMessage());
//            }

        importService.importTasks(mode, tasks);
        User currentUser = authenticationService.getCurrentUser();
        model.addAttribute(AttributeName.JOURNALS,
                taskJournalService.getJournalsByUserId(currentUser.getId()));
        return PathTemplates.HOME;
    }

}
