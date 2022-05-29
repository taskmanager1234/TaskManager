//package com.company.validator.impl;
//
//import com.company.exception.SerializationException;
//import com.company.exception.TaskNotFoundException;
//import com.company.exception.UnexpectedFileExtensionException;
//import com.company.model.Task;
//import com.company.model.User;
//import com.company.service.AuthenticationService;
//import com.company.service.TaskJournalService;
//import com.company.service.TaskService;
//import com.company.service.utils.ConverterFileService;
//import com.company.validator.ValidationReport;
//import com.company.validator.Validator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.*;
//
//@Service
//public class ImportValidator  {
//
//    @Autowired
//    TaskJournalService taskJournalService;
//
//    @Autowired
//    AuthenticationService authenticationService;
//
//    @Autowired
//    TaskService taskService;
//
//    @Autowired
//    ConverterFileService converterFileService;
//
//    @Override
//    public ValidationReport validate(Object object) throws ClassCastException, UnexpectedFileExtensionException, IOException, SerializationException, TaskNotFoundException {
//        MultipartFile multipartFile = (MultipartFile) object;
//        List<Task> tasks = converterFileService.getTasks(multipartFile);
//        ValidationReport validationReport = new ValidationReport();
//
//        if (!isJournalSingleForTasks(tasks)) {
//            validationReport.addError("Journal must be single for tasks");
//        }
//        UUID journalId = tasks.get(0).getTasksJournal().getId();
//
//        if (isJournalAlreadyExist(journalId)) {
//            if (!isJournalFromCurrentUser(journalId)) {
//                validationReport.addError("The log must be owned by the current user");
//            }
//            if (!areTasksFromCurrentUserOrNotExist(tasks)) {
//                validationReport.addError("All tasks must be owned by the current user");
//            }
//            if (isTasksFromAnotherJournal(tasks, journalId)) {
//                validationReport.addError("Existing tasks must belong to the current journal");
//            }
//        }
//        return validationReport;
//    }
//
//
//    private boolean isJournalSingleForTasks(List<Task> tasks) {
//        Set<UUID> journalIds = new HashSet<>();
//        for (Task task : tasks) {
//            UUID journalId = task.getTasksJournal().getId();
//            journalIds.add(journalId);
//        }
//        return journalIds.size() == 1;
//    }
//
//    private boolean isJournalAlreadyExist(UUID journalId) {
//        return Objects.nonNull(taskJournalService.getById(journalId));
//    }
//
//    private boolean isJournalFromCurrentUser(UUID journalId) {
//        User currentUser = authenticationService.getCurrentUser();
//        UUID userId = currentUser.getId();
//        UUID userIdFromJournal = taskJournalService.getById(journalId).getUser().getId();
//        return userId.equals(userIdFromJournal);
//    }
//
//    //все ли указанные таски принадлежат тек юзеру, либо не существуют
//    private boolean areTasksFromCurrentUserOrNotExist(List<Task> tasks) throws TaskNotFoundException {
//        for (Task task : tasks) {
//            if (isTaskFromAnotherUser(task)) {
//                return false;
//            }
//        }
//        return true;
//    }
//    //цель: юзера передаваемой таски, сравнить с юзером текущим
//    private boolean isTaskFromAnotherUser(Task task) throws TaskNotFoundException {
//        User currentUser = authenticationService.getCurrentUser();
//        UUID taskId = task.getId();
//        User user = taskService.getById(taskId).getTasksJournal().getUser();
//        return !user.equals(currentUser);
//    }
//
//    private boolean isTasksFromAnotherJournal(List<Task> tasks, UUID journalId) {
//        for (Task task : tasks) {
//            if (isTaskFromAnotherJournal(task, journalId))
//                return true;
//        }
//        return false;
//    }
//
//
//    private boolean isTaskFromAnotherJournal(Task task, UUID journalId) {
//        try {
//            Task taskFromDb = taskService.getById(task.getId());
//            return !taskFromDb.getTasksJournal().getId().equals(journalId);
//        } catch (TaskNotFoundException e) {
//            return false;
//        }
//    }
//
//}
