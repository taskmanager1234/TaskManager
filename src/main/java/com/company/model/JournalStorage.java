package com.company.model;

import com.company.repository.TaskRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//todo данный класс был актуален при работе с журналом, читаемым из файла. При работе с данными из базы его актальность теряется.
// Какое было назначение этого класса? Хранить в памяти java-представление журнала, которое мы при старте программы прочитали из файла.
// Чтобы нам каждый раз не приходилось перечитывать файл снова, когда мы обращаемся к журналу.
// Прои работе с базой концепция иная. Данные в базе - изменяемые, они могут (и будут) меняться во время работы программы.
// Поэтому если ты один раз при старте загрузишь состояние объектов из базы - ты не сможешь отслеживать, как изменились файлы во время работы.
// Работа с базой данных обычно происходит следующим образом: каждый раз, когда нам нужно получить некоторые объекты, мы ходим за ними в базу. И получаем самое актуальное состояние данных на текущий момент.
// (да, есть всякие кэши, но об этом пока давай думать не будем)
@Service
public class JournalStorage {
    @Autowired
    private TaskRepository taskRepository;

    private static JournalStorage INSTANCE;
    private TasksJournal tasksJournal;
   /// private static Logger logger = Logger.getLogger(JournalStorage.class);

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
