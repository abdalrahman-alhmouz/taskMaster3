package com.example.taskmaster;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    public void saveTask(TaskTable taskTable);

    @Query("SELECT * FROM Task")
    public List<TaskTable> getAllTasks();

    @Query("SELECT * FROM Task ORDER BY id DESC")
    public List<TaskTable> getAllTasksReversed();



}
