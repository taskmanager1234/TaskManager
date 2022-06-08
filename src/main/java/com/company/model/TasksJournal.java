package com.company.model;

import com.company.exception.NoSuchTaskException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@NamedQueries({
        @NamedQuery(
                name = "getJournalsByUserId",
                query = "from TasksJournal tj where tj.user.id = :user_id"
                ),
        @NamedQuery(
                name="getJournals",
                query = "from TasksJournal "
        )
}
)


@Entity
@Table(name = "task_journal", schema = "public", catalog = "postgres")
@Data
public class TasksJournal implements Serializable {

    @Id
    @Column(name = "id", columnDefinition = "varchar(40)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "journal_id")
    private List<Task> tasks;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id",columnDefinition = "varchar(40)")
    @JsonIgnore
    private User user;

    public String getJournalName() {
        return journalName;
    }

    @Basic
    @Column(name = "journal_name", nullable = false, length = 110)
    private String journalName;

    public TasksJournal(UUID id, List<Task> tasks) {
        this.tasks = tasks;
        this.id = Objects.isNull(id) ? UUID.randomUUID() : id;
    }

    public TasksJournal(String journalName) {
        this.journalName = journalName;
        this.tasks = new ArrayList<>();
        this.id = Objects.isNull(id) ? UUID.randomUUID() : id;
    }
    public TasksJournal(){}

    public UUID getId() {
        return id;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setIdUser(UUID id) {
        this.id = id;
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
    }

    public void removeTask(UUID index) {
        tasks.remove(index);
    }

    public Task getTaskById(UUID id) throws NoSuchTaskException {
        Task taskRes = tasks.stream()
                .filter(task -> id.equals(task.getId()))
                .findAny()
                .orElse(null);
        if (taskRes == null)
            throw new NoSuchTaskException("Task with id = " + id + "not found");
        return taskRes;
    }


    public void updateTaskByID(UUID id, Task task) throws NoSuchTaskException {
        ListIterator<Task> iterator = tasks.listIterator();
        while (iterator.hasNext()) {
            UUID next = iterator.next().getId();
            if (next.equals(id))
                iterator.set(task);
        }
        throw new NoSuchTaskException("Task with id = " + id + "not found");
    }

    public boolean containsTask(Task task) {
        return tasks.contains(task);

    }

    @Override
    public String toString() {
        return "TasksJournal{" +
                "task=" + tasks +
                "}";
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
