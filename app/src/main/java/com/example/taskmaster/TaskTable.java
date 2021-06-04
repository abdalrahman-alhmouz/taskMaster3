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

        public TaskTable(String title, String body, String state){
            this.title = title;
            this.body = body;
            this.state = state;
        }
}
