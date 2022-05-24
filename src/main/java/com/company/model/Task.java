package com.company.model;


import com.company.constants.DataBaseCatalogs;
import com.company.constants.DataBaseSchemes;
import com.company.constants.Tables;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


@NamedQueries({
        @NamedQuery(
                name = "findById",
                query = "from Task t where t.id = :id"
        ),
        @NamedQuery(
                name = "getTaskByTaskId",
                query = "from Task t where t.id = :task_id"
        ),
//        @NamedQuery(
//                name="updateJournalIdInTasks",
//                query="update Task set tasksJournal.id = :id where Task.id in (:ids)"
//        )
}
)

@Entity
@Table(name = Tables.TASK, schema = DataBaseSchemes.SCHEMA, catalog = DataBaseCatalogs.CATALOG)
public class Task implements Serializable {
    private static final String DATE_PATTERN = "dd-MM-yyyy HH:mm:ss";
    private static final String TIMEZONE = "Europe/Samara";

    @Id
    @Column(name = "id", columnDefinition = "varchar(40)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Basic
    @Column(name = ColumnNames.TITLE_COLUMN, nullable = false, length = 110)
    private String title;

    @Basic
    @Column(name = ColumnNames.DESCRIPTION_COLUMN, nullable = true, length = 50000)
    private String description;

    @Basic
    @Column(name = ColumnNames.START_DATE_COLUMN, nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = TIMEZONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @Basic
    @Column(name = ColumnNames.END_DATE_COLUMN, nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = TIMEZONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    @Basic
    @Column(name = ColumnNames.REMINDER_COLUMN, nullable = true)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime reminder;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "journal_id")
    private TasksJournal tasksJournal;


    public Task() {
        int a = 5;
    }

    public Task(String title, String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = UUID.randomUUID();
    }

    public Task(UUID id, String title, String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = id;
    }

    public Task(UUID id, String title, String description, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime reminder) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reminder = reminder;
    }


    public UUID getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }


    public LocalDateTime getStartDate() {
        return startDate;
    }


    public LocalDateTime getEndDate() {
        return endDate;
    }


    public LocalDateTime getReminder() {
        return reminder;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setReminder(LocalDateTime reminder) {
        this.reminder = reminder;
    }

    public void setTaskJournal(TasksJournal taskJournal) {
        this.tasksJournal = taskJournal;
    }


    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task that = (Task) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (reminder != null ? !reminder.equals(that.reminder) : that.reminder != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (reminder != null ? reminder.hashCode() : 0);
        return result;
    }

    public TasksJournal getTasksJournal() {
        return tasksJournal;
    }

    public void setTasksJournal(TasksJournal tasksJournal) {
        this.tasksJournal = tasksJournal;
    }

    private static class ColumnNames {
        public static final String TITLE_COLUMN = "title";
        public static final String DESCRIPTION_COLUMN = "description";
        public static final String START_DATE_COLUMN = "start_date";
        public static final String END_DATE_COLUMN = "end_date";
        public static final String REMINDER_COLUMN = "reminder";
    }
}
