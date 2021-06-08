package com.example.taskmaster;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Task")
public class TaskTable {

        @PrimaryKey(autoGenerate = true)
        long id;

        public String title;
        public String body;
        public String state;
         private String location;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public TaskTable(String title, String body, String state){
            this.title = title;
            this.body = body;
            this.state = state;
        }
}
