package com.company.controllers;


import com.company.constants.TaskManagerConstants;
import com.company.exception.*;
import com.company.model.Task;
import com.company.service.TaskJournalService;
import com.company.service.TaskService;
import com.company.service.import_export.ExportService;
import com.company.service.import_export.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ExportController {



    private static class PathVariables {
        public static final String EXTENSION = "extension";
        public static final String TASK_CHECKBOX = "task_checkbox";
    }

    @Autowired
    TaskService taskService;

    @Autowired
    ExportService exportService;

    @Autowired
    ImportService importService;

    @Autowired
    TaskJournalService taskJournalService;


    @PostMapping(value = TaskManagerConstants.EXPORT_URL)
    public ResponseEntity<InputStreamResource> exportTasks(@RequestParam(name = PathVariables.EXTENSION) String extension,
                                                           @RequestParam(name = PathVariables.TASK_CHECKBOX) List<String> tasksIds) throws TaskNotFoundException, UnexpectedFileExtensionException, SerializationException {
        List<Task> tasks = new ArrayList<>();
        try {
            for (int i = 0; i < tasksIds.size(); i++) {
                UUID currentTaskId = UUID.fromString(tasksIds.get(i));
                tasks.add(i, taskService.getById(currentTaskId));
            }
        } catch (NullPointerException e)
        {
            throw new NullPointerException("No tasks selected");
        }
        ResponseEntity<InputStreamResource> responseEntity
                = exportService.exportTasks(tasks, extension);

        return responseEntity;
    }




}
