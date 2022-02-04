package com.company.controllers;

import com.company.constants.ErrorPages;
import com.company.constants.PathTemplates;
import com.company.exception.CreateTaskException;
import com.company.exception.DeleteTaskException;
import com.company.exception.NoSuchTaskException;
import com.company.exception.TaskNotFoundException;
import com.company.model.Task;
import com.company.model.TasksJournal;
import com.company.service.TaskJournalService;
import com.company.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

//ctrl+alt+o
@Controller
@RequestMapping("/tasksJournal/{id}")
public class MainController {

    private static class Endpoints {
        public static final String TASKS = "/tasks";
        public static final String ADD_TASK = "/addTask";
        public static final String MAIN_PAGE = "/";
        public static final String UPDATE_TASK = "/updateTask/{taskId}";
        public static final String DELETE_TASK = "/deleteTask/{taskId}";
    }

    private static class ModelAttributes {
        public static final String TASKS = "tasks";
        public static final String NOT_FOUND_MESSAGE = "message";
        public static final String JOURNAL_ID = "journalId";
    }

    private static class PathVariables {
        public static final String JOURNAL_ID = "id";
        public static final String TASK_ID = "taskId";
    }
    // @Autowired
    // private CancellableScheduler scheduler;//todo что это за класс, почему его нет на git?

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskJournalService taskJournalService;


    @GetMapping(value = Endpoints.TASKS)
    public String tasks(@PathVariable UUID id, Model model) {
        TasksJournal tasksJournal = taskJournalService.getById(id);
        model.addAttribute(ModelAttributes.JOURNAL_ID, id);
        model.addAttribute(ModelAttributes.TASKS, tasksJournal.getTasks()); //в переменную tasks передаем 2 параметр
        return PathTemplates.TASKS;
    }


    @GetMapping(value = Endpoints.ADD_TASK)
    public String showCreateTask(Model model, @PathVariable String id) {
        UUID journalIdReduced = UUID.fromString(id);
        model.addAttribute(ModelAttributes.JOURNAL_ID, journalIdReduced);
        return PathTemplates.ADD_TASK;
    }

    @PostMapping(value = Endpoints.ADD_TASK)
    public String addTaskPost(@PathVariable(name = PathVariables.JOURNAL_ID) String idJournal,
                              @RequestParam String title,
                              @RequestParam String description,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        //todo наш web-контроллер начинает брать на себя слишком много функций. Сейчас он и делает проверки, и работате с базой, и подготавливает данные для отображения на UI.
        // Предлагаю сократить его зону ответственности только до подготовки данных для UI, всю же логику работы с тасками\журналами вынести в отдельные классы-сервисы, TasksService, JournalsService.
        // Сервисы не будут ничего знать о UI, они будут просто предоставялть методы работы с сущностями, CRUD, поиск и т.д.
        Task task = new Task(title, description, startDate, endDate);
        UUID journalIdReduced = UUID.fromString(idJournal);
        try {
            taskService.create(task, journalIdReduced);
        } catch (CreateTaskException e) {
            return ErrorPages.BAD_REQUEST;
        }
        //scheduler.scheduleTask(task);
        return String.format(PathTemplates.REDIRECT_TO_HOME, idJournal);
    }


    @GetMapping(value = Endpoints.MAIN_PAGE)
    public String getStartPage() {
        return PathTemplates.REDIRECT_TO_HOME;
    }

    //todo в REST парадигме запросы типа GET используются только для получения данных, но не для из изменения\удаления. У нас тут явно запутывающий нейминг.
    @GetMapping(Endpoints.UPDATE_TASK)
    public String showUpdateTaskPage(@PathVariable(name = PathVariables.JOURNAL_ID) String idJournal, @PathVariable(name = PathVariables.TASK_ID) String taskId, Model model) {
        try {
            UUID taskIdReduced = UUID.fromString(taskId);
            UUID journalIdReduced = UUID.fromString(idJournal);
            Task task = taskService.getByIdAndByJournalId(taskIdReduced, journalIdReduced);
            if (Objects.nonNull(task)) {
                model.addAttribute(ModelAttributes.JOURNAL_ID, journalIdReduced);
                model.addAttribute(ModelAttributes.TASKS, task);//todo почему для передачи одной таски на страницу отображения параметров таски используется тот же атрибут, который используется для передачи всех тасок на страницу отображения журнала?
            } else {
                throw new NoSuchTaskException("Task with id  = " + taskId + " not found in Journal with id = " + idJournal);
            }
        } catch (NoSuchTaskException e) {
            model.addAttribute(ModelAttributes.NOT_FOUND_MESSAGE, e.getMessage());
            return ErrorPages.NOT_FOUND;//todo каким образом пользователь сможет увидеть сообщение об ошибке?

        }
        return PathTemplates.UPDATE_TASK;
    }


    @PostMapping(Endpoints.UPDATE_TASK)
    public String updateTaskPost(@PathVariable(name = PathVariables.JOURNAL_ID) String idJournal,
                                 @PathVariable(name = PathVariables.TASK_ID) String taskId,
                                 @RequestParam String title,
                                 @RequestParam String description,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                 Model model
    ) {

        //todo зачем мы создаем новый объект таски? А вдруг такой таски в базе нет? Тогда вместо update у нас произойжет create. То ли это поведение, которое мы ожидаем от этого метода?
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

        // JournalStorage.getInstance().getTasksJournal().updateTaskByID(taskId, task);

        taskService.update(task);

        return String.format(PathTemplates.REDIRECT_TO_HOME, idJournal);
    }


    @PostMapping(Endpoints.DELETE_TASK)
    public String deleteTaskPost(@PathVariable(name = PathVariables.JOURNAL_ID) String idJournal, @PathVariable(name = PathVariables.TASK_ID) String taskId, Model model) {
        UUID taskIdNew = UUID.fromString(taskId);
        // JournalStorage.getInstance().getTasksJournal().removeTask(taskId);

        try {
            taskService.deleteTaskById(taskIdNew);
        } catch (DeleteTaskException e) {
            model.addAttribute(ModelAttributes.NOT_FOUND_MESSAGE, e.getMessage());
            return ErrorPages.NOT_FOUND;
        }
        return String.format(PathTemplates.REDIRECT_TO_HOME, idJournal);
    }

}
