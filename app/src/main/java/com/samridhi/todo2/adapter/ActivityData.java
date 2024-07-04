package com.samridhi.todo2.adapter;

import com.samridhi.todo2.data.local.entity.TaskEntity;

import java.util.ArrayList;
import java.util.List;

public class ActivityData {
    public static ArrayList<TaskDetails> taskDetails= new ArrayList<>(); // this is initialized list
    public static List<TaskEntity> taskDataFromDb;  // this is un-initialized list

}
