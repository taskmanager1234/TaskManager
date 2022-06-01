package com.company.service.import_export;

import com.company.exception.*;
import com.company.model.Task;
import com.company.model.TasksJournal;
import com.company.model.User;
import com.company.repository.JournalRepository;
import com.company.repository.TaskRepository;
import com.company.repository.UserRepository;
import com.company.service.AuthenticationService;
import com.company.service.TaskService;
import com.company.service.utils.ConverterFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImportService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    JournalRepository journalRepository;

    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final AuthenticationService authenticationService;
    private final ConverterFileService converterFileService;

    @Autowired
    public ImportService(TaskService taskService,
                         TaskRepository taskRepository,
                         AuthenticationService authenticationService,
                         ConverterFileService converterFileService) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
        this.authenticationService = authenticationService;
        this.converterFileService = converterFileService;
    }


    public void importTasks(String mode, MultipartFile file) throws BadImportException, IOException, SerializationException, CreateTaskException, UnexpectedFileExtensionException {
        List<Task> tasks = converterFileService.getTasks(file);
        User currentUser = authenticationService.getCurrentUser();
        TasksJournal journal = tasks.get(0).getTasksJournal();
        journal.setUser(currentUser);

        if (ImportMode.IGNORE.equals(mode)) {
            importWithIgnore(tasks);
        } else if (ImportMode.REPLACE.equals(mode)) {
            importWithReplace(tasks);
        } else {
            throw new BadImportException("Incorrect mode");
        }


    }

    private void importWithIgnore(List<Task> tasks) throws CreateTaskException {
        for (Task task : tasks) {
            try {
                taskService.getById(task.getId());
            } catch (TaskNotFoundException e) {
                taskService.create(task);
            }
        }
    }

    private void importWithReplace(List<Task> tasks) throws CreateTaskException {
        for (Task task : tasks) {
            try {
                UUID idJournal = task.getTasksJournal().getId();
                String idJournalString = idJournal.toString();
                taskService.getById(task.getId());
                taskService.update(task);
                UUID idCurrentUser = authenticationService.getCurrentUser().getId();

                List<String> idTasks = new ArrayList<>();
                for (int i = 0; i < tasks.size(); i++) {
                    idTasks.add(tasks.get(i).getId().toString());
                }
                taskRepository.updateJournalIdInTasks(idJournalString, idTasks);
                //journalRepository.setUserId(idCurrentUser);

            } catch (TaskNotFoundException e) {
                taskService.create(task);

            }
        }
    }




}
