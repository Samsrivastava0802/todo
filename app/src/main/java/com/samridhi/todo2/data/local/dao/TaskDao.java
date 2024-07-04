package com.samridhi.todo2.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.samridhi.todo2.data.local.entity.TaskEntity;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDao {
    @Query("Select * from TaskEntity")
    List<TaskEntity> getAllTaskData();

    @Insert
    void addTaskData(TaskEntity taskEntity);

    @Delete
    void deleteTaskData(TaskEntity taskEntity);

    @Update
    void updateTaskData(TaskEntity taskEntity);

}
