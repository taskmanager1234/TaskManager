package com.company.controllers;

import com.company.constants.ErrorPages;
import com.company.constants.PathTemplates;
import com.company.exception.CreateTaskException;
import com.company.exception.DeleteTaskException;
import com.company.exception.NoSuchTaskException;
import com.company.exception.TaskNotFoundException;
import com.company.model.Task;
import com.company.model.TasksJournal;
import com.company.service.SearchService;
import com.company.service.TaskJournalService;
import com.company.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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


    //todo: полагаю, эти классы так же стоит вынести отдельно и сделать публичными
    private static class Endpoints {
        public static final String TASKS = "/tasks";
        //todo: зачем две одинаковые константы? Так же константа должена в названии передавать то, чем она является.
        // ADD_TASK_URL - нормальное имя для константы. SHOW_TASK_CREATION_FORM - плохое, потому что константа ничего не показывает, ее имя не может быьть глаголом.
        public static final String ADD_TASK = "/addTask";
        public static final String SHOW_TASK_CREATION_FORM = "/addTask";
        public static final String MAIN_PAGE = "/";
        public static final String UPDATE_TASK = "/updateTask/{taskId}";
        public static final String SHOW_TASK_UPDATE_FORM = "/updateTask/{taskId}";
        public static final String SEARCH_TASKS = "/searchTasks";
        public static final String MOVE_TASKS = "/moveTasks";
        public static final String MULTIPLE_FORM = "/MultipleForm";
    }

    private static class ModelAttributes {
        public static final String TASKS = "tasks";
        public static final String TASK = "task";
        public static final String NOT_FOUND_MESSAGE = "message";
        public static final String JOURNAL_ID = "journalId";
        public static final String CONDITIONS = "conditions";
        public static final String JOURNALS = "journals";
    }

    private static class PathVariables {
        public static final String JOURNAL_ID = "id";
        public static final String TASK_ID = "taskId";
        public static final String VALUE = "value";
        public static final String FIELD = "field";
        public static final String SEARCH_OPTION = "search_option";
        public static final String TASK_CHECKBOX = "task_checkbox";
        public static final String SELECTED_JOURNAL = "selected_journal";
       // public static final String TASK_CHECKBOX = "task_checkbox";

    }

    public static class JsonKey {
        public static final String JOURNAL_ID_FOR_IMPORT = "journal_to";
        public static final String TASKS_SWAP = "ids";
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

    //todo: аналогично - согласованные имена, убрать не используемое, константы
    @GetMapping(value = Endpoints.TASKS)
    public String showTaskManager(Authentication authentication, @PathVariable UUID id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current user: " + auth.getName());
        System.out.println("Current user: " + auth.getAuthorities());

        TasksJournal tasksJournal = taskJournalService.getById(id);
        model.addAttribute(ModelAttributes.JOURNAL_ID, id);
        model.addAttribute(ModelAttributes.TASKS, tasksJournal.getTasks()); //в переменную tasks передаем 2 параметр
        List<TasksJournal> tasksJournals = taskJournalService.getJournals();
        model.addAttribute("journals", tasksJournals);

        model.addAttribute("conditions", SearchService.Condition.values());
        //model.addAttribute("fiedls", SearchService.Field.values());
        return PathTemplates.TASKS;
    }

    @GetMapping(value = Endpoints.SHOW_TASK_CREATION_FORM)
    public String showTaskCreateForm(Model model, @PathVariable String id) {
        UUID journalIdReduced = UUID.fromString(id);
        model.addAttribute(ModelAttributes.JOURNAL_ID, journalIdReduced);
        return PathTemplates.ADD_TASK;
    }

    @PostMapping(value = Endpoints.ADD_TASK)
    public String createTask(@PathVariable(name = PathVariables.JOURNAL_ID) String idJournal,
                             @RequestParam String title,
                             @RequestParam String description,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                             Model model
    ) {
        Task task = new Task(title, description, startDate, endDate);
        UUID journalIdReduced = UUID.fromString(idJournal);
        try {
            task.setTasksJournal(taskJournalService.getById(journalIdReduced));
            taskService.create(task);
        } catch (CreateTaskException e) {
            //todo: протестировать сценарий, когда какие-то параметры не заполнены или заполнены не верно.
            // Ожидаемое поведение: пользователю отобразится страница ошибки с четким и понятным сообщением об ошибке
            return ErrorPages.BAD_REQUEST;
        }
        model.addAttribute("journal_id", journalIdReduced);
        //scheduler.scheduleTask(task);
        return String.format(PathTemplates.REDIRECT_TO_HOME, idJournal);
    }


    @GetMapping(value = Endpoints.MAIN_PAGE)
    public String getStartPage() {
        return PathTemplates.REDIRECT_TO_HOME;
    }

    @GetMapping(Endpoints.SHOW_TASK_UPDATE_FORM)
    public String showTaskUpdateForm(@PathVariable(name = PathVariables.JOURNAL_ID) String idJournal, @PathVariable(name = PathVariables.TASK_ID) String taskId, Model model) {
        try {
            UUID taskIdReduced = UUID.fromString(taskId);
            UUID journalIdReduced = UUID.fromString(idJournal);
            Task task = taskService.getByIdAndByJournalId(taskIdReduced, journalIdReduced);
            if (Objects.nonNull(task)) {
                model.addAttribute(ModelAttributes.JOURNAL_ID, journalIdReduced);
                model.addAttribute(ModelAttributes.TASK, task);
            } else {
                throw new NoSuchTaskException("Task with id  = " + taskId + " not found in Journal with id = " + idJournal);
            }
        } catch (NoSuchTaskException e) {
            model.addAttribute(ModelAttributes.NOT_FOUND_MESSAGE, e.getMessage());
            return ErrorPages.NOT_FOUND;

        }
        return PathTemplates.UPDATE_TASK;
    }


    @PostMapping(Endpoints.UPDATE_TASK)
    public String updateTask(@PathVariable(name = PathVariables.JOURNAL_ID) String idJournal,
                             @PathVariable(name = PathVariables.TASK_ID) String taskId,
                             @RequestParam String title,
                             @RequestParam String description,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                             Model model
    ) {
        UUID taskIdNew = UUID.fromString(taskId);
        Task task;
        try {
            task = taskService.getById(taskIdNew);
        } catch (TaskNotFoundException e) {
            model.addAttribute(ModelAttributes.NOT_FOUND_MESSAGE, e.getMessage());
            return ErrorPages.NOT_FOUND;
        }
        task.setTitle(title);
        task.setDescription(description);
        task.setStartDate(startDate);
        task.setEndDate(endDate);


        taskService.update(task);

        return String.format(PathTemplates.REDIRECT_TO_HOME, idJournal);
    }



    @PostMapping(Endpoints.MULTIPLE_FORM)
    public String deleteTasks(@PathVariable(name = PathVariables.JOURNAL_ID) String idJournal,
                              @RequestParam(name = PathVariables.TASK_CHECKBOX) String[] ids,
                              Model model) {

        for (String currentId : ids) {
            try {
                taskService.deleteTaskById(UUID.fromString(currentId));
            } catch (DeleteTaskException e) {
                model.addAttribute(ModelAttributes.NOT_FOUND_MESSAGE, e.getMessage());
                return ErrorPages.NOT_FOUND;
            }
        }

        return String.format(PathTemplates.REDIRECT_TO_HOME, idJournal);
    }


    @PostMapping(Endpoints.SEARCH_TASKS)
    public String searchTasks(@RequestParam(PathVariables.VALUE) String value,
                              @PathVariable(name = PathVariables.JOURNAL_ID) String idJournal,
                              @RequestParam(PathVariables.SEARCH_OPTION) String searchOption,
                              @RequestParam(PathVariables.FIELD) String field,
                              Model model) {
        UUID idJournal1 = UUID.fromString(idJournal);
        List<Task> tasks;
        //todo: нет контроля входящих значений - если например передать в field 'sadff' - запрос упадет
        SearchService.Field field1 = SearchService.Field.valueOf(field.toUpperCase());
        SearchService.Condition condition = SearchService.Condition.valueOf(searchOption.toUpperCase());
        SearchService.Criterion criterion = new SearchService.Criterion(field1, condition);
        tasks = searchService.searchTasksByCriterion(criterion, value, idJournal1);

        model.addAttribute(ModelAttributes.TASKS, tasks);
        model.addAttribute(ModelAttributes.CONDITIONS, SearchService.Condition.values());
        List<TasksJournal> tasksJournals = taskJournalService.getJournals();
        model.addAttribute(ModelAttributes.JOURNALS, tasksJournals);
        model.addAttribute(ModelAttributes.JOURNAL_ID, idJournal);
        return PathTemplates.TASKS;

    }

    //todo: согласованные названия
    @PostMapping(value = Endpoints.MOVE_TASKS)
    public String swapTasks(@RequestParam(name = PathVariables.TASK_CHECKBOX) List<String> tasksIds,
                            @RequestParam(name = PathVariables.SELECTED_JOURNAL) String journalId) {

        taskService.updateJournalIdInTasks(journalId, tasksIds);
        return String.format(PathTemplates.REDIRECT_TO_HOME, journalId);

    }

}
