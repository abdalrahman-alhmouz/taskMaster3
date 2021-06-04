package com.example.taskmaster;

import androidx.room.RoomDatabase;


@androidx.room.Database(entities = {TaskTable.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract TaskDao taskDao();
}
