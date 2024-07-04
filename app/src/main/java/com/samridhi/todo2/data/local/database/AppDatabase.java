package com.samridhi.todo2.data.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.samridhi.todo2.data.local.dao.TaskDao;
import com.samridhi.todo2.data.local.entity.TaskEntity;

@Database(entities = {TaskEntity.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    private static  AppDatabase instance;

    public static AppDatabase getDbInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "todo2").allowMainThreadQueries().build();
        }
        return instance;
    }

}
