package com.company.controllers;

import com.company.constants.Endpoints;
import com.company.constants.ErrorPages;
import com.company.constants.PathTemplates;
import com.company.exception.SerializationException;
import com.company.helpers.WriterToFile;
import com.company.model.JournalStorage;
import com.company.model.TasksJournal;
import com.company.serializer.impl.JsonSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.IOException;

@Controller
public class FileController {
    private static final String TASK_MANAGER_FILE_PATH = "${task_manager.in_memory.filepath}";

    @Value(TASK_MANAGER_FILE_PATH)
    private String filePath;

    @PostMapping(value = Endpoints.SAVE_TASK_JOURNAL)
    public String saveTaskJournalInFile(){
        TasksJournal tasksJournal = JournalStorage.getInstance().getTasksJournal();
        JsonSerializer jsonSerializer = new JsonSerializer();
        Object o = null;
        try {
             o = jsonSerializer.serialize(tasksJournal);
        } catch (SerializationException e) {
            return ErrorPages.INTERNAL_SERVER_ERROR;
            //в случае если программа не смогла обработать запрос прогр должна вывести сообщение пользователю об ошибке (страничка в браузере)
            //добавить в issue
        }
        try {
            //todo надо избавляться от "magic numbers". Т.е. от неименованых литералов в коде.
            // Либо должны быть именовванные константы, либо - какие-то проперти, которые задаются извне.
            WriterToFile.writeToFileAsText((String)o, filePath);
            //почитать про проперти ява и передавать имя файла из которого грузим журнал в пропертях
            //добавить класс для работы с пропертями добавить в ишью
        } catch (IOException e) {
            return ErrorPages.NOT_FOUND;//todo что это?
        }
        //todo аналогично. Это напрашивается, как константа. К тому же почему до сих пор test?
        return PathTemplates.REDIRECT_TO_HOME;
        // класс констант с url
    }
}
