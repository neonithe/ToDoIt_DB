package se.lexicon.model;

import java.util.Objects;

public class ToDo {

    private int id;
    private String title;
    private String description;
    private String deadLine;
    private boolean done;
    private int assigneeId;

    public ToDo(int id, String title, String description, String deadLine, boolean done, int assigneeId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.done = done;
        this.assigneeId = assigneeId;
    }

    public ToDo(String title, String description, String deadLine, boolean done) {
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.done = done;
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

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
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

    public void setAssigneeId(int assigneeId) {
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
