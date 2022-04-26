package com.company.controllers;

import com.company.constants.AttributeName;
import com.company.constants.ErrorPages;
import com.company.constants.PathTemplates;
import com.company.constants.TaskManagerConstants;
import com.company.exception.*;
import com.company.model.Task;
import com.company.model.TasksJournal;
import com.company.service.SearchService;
import com.company.service.TaskJournalService;
import com.company.service.TaskService;
import com.company.service.utils.Condition;
import com.company.service.utils.Field;
import com.company.validator.impl.TaskValidator;
import com.company.validator.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

//ctrl+alt+o
@Controller
@RequestMapping("/tasksJournal/{id}")
public class MainController {


    private static class PathVariables {
        public static final String JOURNAL_ID = "id";
        public static final String TASK_ID = "taskId";
        public static final String VALUE = "value";
        public static final String FIELDS = "fields";
        public static final String SEARCH_OPTION = "search_option";
        public static final String TASK_CHECKBOX = "task_checkbox";
        public static final String SELECTED_JOURNAL = "selected_journal";

    }


    private final TaskService taskService;
    private final TaskJournalService taskJournalService;
    private final SearchService searchService;

    @Autowired
    public MainController(TaskService taskService, TaskJournalService taskJournalService, SearchService searchService) {
        this.taskService = taskService;
        this.taskJournalService = taskJournalService;
        this.searchService = searchService;
    }


    @GetMapping(value = TaskManagerConstants.TASKS_URL)
    public String getTasks(@PathVariable UUID id, Model model) {

        TasksJournal tasksJournal = taskJournalService.getById(id);
        model.addAttribute(AttributeName.JOURNAL_ID, id);
        model.addAttribute(AttributeName.CURRENT_JOURNAL_NAME, tasksJournal.getJournalName());
        model.addAttribute(AttributeName.TASKS, tasksJournal.getTasks());
        List<TasksJournal> tasksJournals = taskJournalService.getJournals();
        model.addAttribute(AttributeName.JOURNALS, tasksJournals);

        model.addAttribute(AttributeName.CONDITIONS, Condition.stringValues());
        model.addAttribute(PathVariables.FIELDS, Field.stringValues());
        return PathTemplates.TASKS;
    }

    @GetMapping(value = TaskManagerConstants.CREATE_TASK_URL)
    public String getTaskCreateForm(Model model, @PathVariable String id) {
        UUID journalIdReduced = UUID.fromString(id);
        model.addAttribute(AttributeName.JOURNAL_ID, journalIdReduced);
        return PathTemplates.CREATE_TASK;
    }

    @PostMapping(value = TaskManagerConstants.CREATE_TASK_URL)
    public String createTask(@PathVariable(name = PathVariables.JOURNAL_ID) String idJournal,
                             Task task,
//                             @RequestParam String title,
//                             @RequestParam String description,
//                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                             Model model
    ) {
        //Task task = new Task(title, description, startDate, endDate);
        UUID journalIdReduced = UUID.fromString(idJournal);
        UUID taskId = UUID.randomUUID();
        task.setId(taskId);
        try {
            TaskValidator taskValidator = new TaskValidator();
            ValidationError validationError = taskValidator.validate(task);
            if (!validationError.isEmpty())
                throw new ValidationException(validationError.getAllErrorsAsString());
            task.setTasksJournal(taskJournalService.getById(journalIdReduced));
            taskService.create(task);
        } catch (CreateTaskException e) {
            //todo: протестировать сценарий, когда какие-то параметры не заполнены или заполнены не верно.
            // Ожидаемое поведение: пользователю отобразится страница ошибки с четким и понятным сообщением об ошибке
            return showErrorPage(ErrorPages.INTERNAL_SERVER_ERROR, model, e.getMessage());
        } catch (ValidationException e) {
            return showErrorPage(ErrorPages.BAD_REQUEST, model, e.getMessage());
        }
        model.addAttribute(AttributeName.JOURNAL_ID_REDUCED, journalIdReduced);
        //scheduler.scheduleTask(task);
        return String.format(PathTemplates.REDIRECT_TO_HOME, idJournal);
    }


    @GetMapping(value = TaskManagerConstants.MAIN_PAGE_URL)
    public String getStartPage() {
        return PathTemplates.REDIRECT_TO_HOME;
    }

    @GetMapping(TaskManagerConstants.SHOW_TASK_UPDATE_FORM_URL)
    public String getTaskUpdateForm(@PathVariable(name = PathVariables.JOURNAL_ID) String idJournal,
                                    @PathVariable(name = PathVariables.TASK_ID) String taskId,
                                    Model model) {
        try {
            UUID taskIdReduced = UUID.fromString(taskId);
            UUID journalIdReduced = UUID.fromString(idJournal);
            Task task = taskService.getByIdAndByJournalId(taskIdReduced);
            if (Objects.nonNull(task)) {
                model.addAttribute(AttributeName.JOURNAL_ID, journalIdReduced);
                model.addAttribute(AttributeName.TASK, task);
            } else {
                throw new NoSuchTaskException("Task with id  = " + taskId + " not found in Journal with id = " + idJournal);
            }
        } catch (NoSuchTaskException e) {
            model.addAttribute(AttributeName.NOT_FOUND_MESSAGE, e.getMessage());
            return ErrorPages.NOT_FOUND;

        }
        return PathTemplates.UPDATE_TASK;
    }


    @PostMapping(value = TaskManagerConstants.UPDATE_TASK_URL, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateTask(@PathVariable(name = PathVariables.JOURNAL_ID) String idJournal,
                             @PathVariable(name = PathVariables.TASK_ID) String taskIdString,
                             Task taskNew,
//                             @RequestParam String title,
//                             @RequestParam String description,
//                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                             Model model
    ) {
        UUID taskId = UUID.fromString(taskIdString);
        Task task;
        try {
            task = taskService.getById(taskId);
        } catch (TaskNotFoundException e) {
            model.addAttribute(AttributeName.NOT_FOUND_MESSAGE, e.getMessage());
            return ErrorPages.NOT_FOUND;
        }
        taskNew.setId(taskId);
        taskNew.setTasksJournal(task.getTasksJournal());
        taskService.update(taskNew);

        return String.format(PathTemplates.REDIRECT_TO_HOME, idJournal);
    }


    @PostMapping(TaskManagerConstants.MULTIPLE_FORM_URL)
    public String deleteTasks(@PathVariable(name = PathVariables.JOURNAL_ID) String idJournal,
                              @RequestParam(name = PathVariables.TASK_CHECKBOX) String[] tasksIds
    ) {

        for (String currentId : tasksIds) {
            taskService.deleteTaskById(UUID.fromString(currentId));
        }

        return String.format(PathTemplates.REDIRECT_TO_HOME, idJournal);
    }


    @PostMapping(TaskManagerConstants.SEARCH_TASKS_URL)
    public String searchTasks(@RequestParam(PathVariables.VALUE) String value,
                              @PathVariable(name = PathVariables.JOURNAL_ID) String idJournalString,
                              @RequestParam(PathVariables.SEARCH_OPTION) String searchOption,
                              @RequestParam(PathVariables.FIELDS) String searchField,
                              Model model) throws BadCriterionException {
        UUID idJournal = UUID.fromString(idJournalString);
        List<Task> tasks;
        Field field1 = Field.valueOf(searchField.toUpperCase());
        Condition condition = Condition.getByStringValue(searchOption);
        SearchService.Criterion criterion = new SearchService.Criterion(field1, condition);
        tasks = searchService.searchTasksByCriterion(criterion, value, idJournal);

        model.addAttribute(AttributeName.TASKS, tasks);
        model.addAttribute(AttributeName.CONDITIONS, Condition.stringValues());
        model.addAttribute(AttributeName.FIELDS, Field.stringValues());
        List<TasksJournal> tasksJournals = taskJournalService.getJournals();
        model.addAttribute(AttributeName.JOURNALS, tasksJournals);
        model.addAttribute(AttributeName.JOURNAL_ID, idJournalString);
        return PathTemplates.TASKS;

    }

    @PostMapping(value = TaskManagerConstants.MOVE_TASKS_URL)
    public String moveTasks(@RequestParam(name = PathVariables.TASK_CHECKBOX) List<String> tasksIds,
                            @RequestParam(name = PathVariables.SELECTED_JOURNAL) String journalId) {

        taskService.updateJournalIdInTasks(journalId, tasksIds);
        return String.format(PathTemplates.REDIRECT_TO_HOME, journalId);

    }

    private String showErrorPage(String page, Model model, String errors) {
        model.addAttribute(AttributeName.ERROR_MESSAGE, errors);
        return page;
    }

}
