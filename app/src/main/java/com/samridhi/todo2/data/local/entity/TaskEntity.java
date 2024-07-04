package com.samridhi.todo2.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    // ye bhi ek column hota hai
    public int uid;

    @ColumnInfo
    public String title;


    @ColumnInfo(name = "samridhi")
    public String description;

}
