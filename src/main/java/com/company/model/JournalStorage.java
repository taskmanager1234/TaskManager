package com.company.model;

//todo отражать в имене, что он singleton, не обязательно

import com.company.JournalLoader;
import com.company.exception.SerializationException;
import com.company.repository.TaskRepository;
import com.company.repository.TaskRepositoryFactory;
import com.sun.xml.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

//todo мне кажется, назначение этого класса сейчас достаточно размытое. По факту, нам нужен класс, котрый будет хранить объект журнала, с которым мы работаем, в памяти.
//и жизненный цикл журнала будет таким: загружен из файла, десериализован, помещен в in-memory хранилище. Его можно из этого хранилища получить, поменять. И в конце работы программы то, что лежит в in-memory хранилище, снова сохраняется на диск.
//И программа не должна давать возможностей сделать что-то иначе, только задуманным программистом образом.
@Service
public class JournalStorage {
    @Autowired
    private TaskRepository taskRepository;
    private static JournalStorage INSTANCE;
    private TasksJournal tasksJournal;
    private static Logger logger = Logger.getLogger(JournalStorage.class);

    private JournalStorage() {
    }

    public static JournalStorage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JournalStorage();
          //  try {
                List<Task> tasks = (List<Task>)INSTANCE.taskRepository.findAll();
            //TODO поменять
                INSTANCE.tasksJournal = new TasksJournal(null,tasks);
                        //JournalLoader.getFromFile();
//            } catch (SerializationException e) {
//                logger.warn("Could not load TasksJournal from file", e);
//                INSTANCE.tasksJournal = new TasksJournal();
//            }
       }
        return INSTANCE;
    }

    public TasksJournal getTasksJournal() {
        return tasksJournal;
    }


}
