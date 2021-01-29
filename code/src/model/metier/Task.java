package model.metier;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {

    private static final long serialVersionUID = 3487408127741490246L;

    private final String title;
    private final String description;

    private final LocalDate date;
    private Boolean done;

    public Task(String title, String description, LocalDate date) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.done = false;
    }

    public Boolean isDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        String toString = "";
        if(isDone()){
            toString+="(Fait) ";
        }
        toString+=title;
        return toString;
    }
}
