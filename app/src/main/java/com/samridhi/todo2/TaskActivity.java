package com.samridhi.todo2;

import static com.samridhi.todo2.adapter.ActivityData.taskDataFromDb;
import static com.samridhi.todo2.adapter.ActivityData.taskDetails;


import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Delete;

import com.samridhi.todo2.adapter.ActivityData;
import com.samridhi.todo2.adapter.TaskAdapter;
import com.samridhi.todo2.adapter.TaskDetails;
import com.samridhi.todo2.data.local.database.AppDatabase;
import com.samridhi.todo2.data.local.database.AppDatabase_Impl;
import com.samridhi.todo2.data.local.entity.TaskEntity;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity implements TaskAdapter.EditTask {
    private SearchView searchText;
    private ImageView addIcon;

    private RecyclerView usersRv;
    private LinearLayoutManager linearLayoutManager;
    private TaskAdapter taskAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toast.makeText(this, "onCreate Called", Toast.LENGTH_SHORT).show();
        //   btnEdit = findViewById(R.id.btn_edit);

        init();
       // loadSomeDummyData();
        getAllTaskData();

        initRecyclerView();

        initListener();

    }

    private void init() {
        usersRv = findViewById(R.id.task_rv);
        searchText = findViewById(R.id.search_et);
        addIcon = findViewById(R.id.iv_add_task);
    }

    private void initRecyclerView() {
        usersRv.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        usersRv.setLayoutManager(linearLayoutManager);
        taskAdapter = new TaskAdapter(this, taskDataFromDb, this);
        usersRv.setAdapter(taskAdapter);
    }

    private void initListener() {
        addIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                navigateToCreateTask();
            }
        });

        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                Log.d("Query", "line no 82: "+ s);
                return true;
            }
        }

        );
    }
    private void filterList(String text) {
        // eg - text-> A, a, abA
        List<TaskEntity> filteredEntityList = new ArrayList();
        // TaskEntity item : taskDataFromDb
        // 1. how does the individual item of the list looks
        // 2. name it to some variable
        // 3. : taskDataFromDb - list 
        for (TaskEntity item : taskDataFromDb) { // jb ek list ko iterate karna ho uske har ek item ke liye
            if(!text.isBlank()){
                if (item.title.trim().toLowerCase().contains(text.trim().toLowerCase())) {
                    //Log.d("Query", "line no 96: " + item.getTitle());
                    filteredEntityList.add(item);
                }
            }
        }
        //Log.d("Query", "line no 100: " + filteredEntityList);
        for(TaskEntity todo : filteredEntityList ){
            Log.d("Query", "line no 117: " + todo.title);
            Log.d("Query", "line no 118: " + todo.description);
        }
        Log.d(TaskAdapter.TAG, "filterList: " +taskDataFromDb);
        taskAdapter.setFilteredList(filteredEntityList);
        taskAdapter.notifyDataSetChanged();
    }

    public void navigateToCreateTask() {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        startActivity(intent);
    }

    private void loadSomeDummyData() {
        taskDetails.add(new TaskDetails("David", "Music lover and guitar player."));
        taskDetails.add(new TaskDetails("Ella", "Bookworm and coffee addict."));
        taskDetails.add(new TaskDetails("Frank", "Fitness freak and marathon runner."));
        taskDetails.add(new TaskDetails("Grace", "Art enthusiast and painter."));
        taskDetails.add(new TaskDetails("Henry", "Tech geek and gadget collector."));
        taskDetails.add(new TaskDetails("Ivy", "Foodie and amateur chef."));
        taskDetails.add(new TaskDetails("Jack", "Travel enthusiast and adventure seeker."));
        taskDetails.add(new TaskDetails("Katie", "Animal lover and pet parent."));
        taskDetails.add(new TaskDetails("Liam", "Film buff and movie critic."));
        taskDetails.add(new TaskDetails("Mia", "Fashionista and trendsetter."));


    }
    private void getAllTaskData() {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

        taskDataFromDb = db.taskDao().getAllTaskData();
        for(TaskEntity taskData : taskDataFromDb){
            Log.d("data", "uid: "+ taskData.uid);
            Log.d("data", "title: "+ taskData.title);
            Log.d("data", "description: "+ taskData.description);

        }


    }


    @Override
    //here we are defining the function
    public void onEditButtonClick(TaskEntity taskDataFromDb, int index) {
        Log.d("onClickTask", " line number 80 " + taskDataFromDb.title);
       Log.d("onClickTask", " line number 81 " + taskDataFromDb.description);
        Log.d("onClickTask", " line number 82 " + taskDataFromDb);
       // Log.d("onClickTask", " line number 83 " + index);
        Intent intent = new Intent(this, CreateTaskActivity.class);
        // intent ka obj ban gya usi intent ka put extra use kar rhe hai jisse
        // hmlog data bhej rhe hai isdar se udhar
        intent.putExtra("title", taskDataFromDb.title);
        intent.putExtra("description", taskDataFromDb.description);
        intent.putExtra("index", index);
        startActivity(intent);

    }
}