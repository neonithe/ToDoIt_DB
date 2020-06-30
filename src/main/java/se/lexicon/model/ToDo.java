package se.lexicon.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class ToDo {

    private int id;
    private String title;
    private String description;
    private LocalDate deadLine;
    private boolean done;
    private Integer assigneeId;

    public ToDo(int id, String title, String description, LocalDate deadLine, boolean done,Integer assigneeId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.done = done;
        this.assigneeId = assigneeId;
    }

    public ToDo(String title, String description, LocalDate deadLine, boolean done, Integer assigneeId) {
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.done = done;
        this.assigneeId = assigneeId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        this.deadLine = deadLine;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Integer assigneeId) {
        this.assigneeId = assigneeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDo toDo = (ToDo) o;
        return id == toDo.id &&
                done == toDo.done &&
                assigneeId == toDo.assigneeId &&
                Objects.equals(title, toDo.title) &&
                Objects.equals(description, toDo.description) &&
                Objects.equals(deadLine, toDo.deadLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, deadLine, done, assigneeId);
    }

    @Override
    public String toString() {
        return "ToDo: " +
                " Id: " + id +
                " | Ttitle: " + title +
                " | Description: " + description +
                " | DeadLine: " + deadLine +
                " | Done: " + done +
                " | AssigneeId: " + assigneeId;
    }
}
