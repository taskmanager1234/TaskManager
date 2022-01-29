package com.company.model;


import com.company.constants.DataBaseCatalogs;
import com.company.constants.DataBaseSchemes;
import com.company.constants.Tables;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = Tables.TASK, schema = DataBaseSchemes.SCHEMA, catalog = DataBaseCatalogs.CATALOG)
public class Task implements Serializable {
    private static final String DATE_PATTERN = "dd-MM-yyyy HH:mm:ss";
    private static final String TIMEZONE = "Europe/Samara";

    private UUID id;

    private String title;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = TIMEZONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = TIMEZONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime reminder;

    @ManyToOne
    @JoinColumn (name = "tasks_journal_id", columnDefinition = "varchar(40)")
    private TasksJournal tasksJournal;

    public Task() {
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

    @Id
    @Column(name = ColumnNames.ID_COLUMN, columnDefinition = "varchar(40)")
    //@Type(type = "uuid-char")
    public UUID getId() {
        return id;
    }

    @Basic
    @Column(name = ColumnNames.TITLE_COLUMN, nullable = false, length = 110)
    public String getTitle() {
        return title;
    }

    @Basic
    @Column(name = ColumnNames.DESCRIPTION_COLUMN, nullable = true, length = 50000)
    public String getDescription() {
        return description;
    }

    @Basic
    @Column(name = ColumnNames.START_DATE_COLUMN, nullable = false)
    public LocalDateTime getStartDate() {
        return startDate;
    }

    @Basic
    @Column(name = ColumnNames.END_DATE_COLUMN, nullable = false)
    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Basic
    @Column(name = ColumnNames.REMINDER_COLUMN, nullable = true)
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
  //  @Type(type = "uuid-char")
   // @Column(name = ColumnNames.ID_JOURNAL_COLUMN, columnDefinition = "varchar(40)")
    public TasksJournal getTasksJournalId() {
        return tasksJournal;
    }
    public void setTasksJournalId(TasksJournal newTaskJournal) {
        this.tasksJournal = newTaskJournal;
    }

    private static class ColumnNames {
        public static final String ID_COLUMN = "id";
        public static final String TITLE_COLUMN = "title";
        public static final String DESCRIPTION_COLUMN = "description";
        public static final String START_DATE_COLUMN = "start_date";
        public static final String END_DATE_COLUMN = "end_date";
        public static final String REMINDER_COLUMN = "reminder";
        public static final String ID_JOURNAL_COLUMN = "tasks_journal_id";
    }
}
