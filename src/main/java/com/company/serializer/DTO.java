package com.company.serializer;

import com.company.model.Task;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DTO {//TODO JournalDto
    @JsonProperty("id")
    private String id;
    @JsonProperty("journalName")
    private String journalName;
    @JsonProperty("tasks")
    private List<Task> tasks = null;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("journalName")
    public String getJournalName() {
        return journalName;
    }

    @JsonProperty("journalName")
    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    @JsonProperty("tasks")
    public List<Task> getTasks() {
        return tasks;
    }

    @JsonProperty("tasks")
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
