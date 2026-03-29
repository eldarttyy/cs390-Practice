package todo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("todo-items")
public class TodoItem {

    @Id
    private String id;

    private LocalDate due;
    private boolean done;
    private String description;

    public TodoItem() {
        id = null;
        due = null;
        done = false;
        description = "";
    }

    public TodoItem( String desc, LocalDate due ) {
        description = desc;
        done = false;
        id = null;
        this.due = due;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDue() {
        return due;
    }

    public void setDue(LocalDate due) {
        this.due = due;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
