package com.company.serializer;

import com.company.model.Task;
import com.company.model.TasksJournal;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
public class JournalDto {
    @JsonProperty("id") //Альтернатива @JsonGetter
    private String id;
    @JsonProperty("journalName")
    private String journalName;
    @JsonProperty("tasks")
    private List<Task> tasks = null;

    public JournalDto(TasksJournal tasksJournal){
        this.id = tasksJournal.getId().toString();
        this.journalName = tasksJournal.getJournalName();
        this.tasks = tasksJournal.getTasks();
    }

    public JournalDto() {

    }
}
