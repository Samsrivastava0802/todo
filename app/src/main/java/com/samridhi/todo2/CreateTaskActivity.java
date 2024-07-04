package com.samridhi.todo2;

import static com.samridhi.todo2.adapter.ActivityData.taskDataFromDb;
import static com.samridhi.todo2.adapter.ActivityData.taskDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.samridhi.todo2.adapter.ActivityData;
import com.samridhi.todo2.adapter.TaskDetails;
import com.samridhi.todo2.data.local.database.AppDatabase;
import com.samridhi.todo2.data.local.entity.TaskEntity;

public class CreateTaskActivity extends AppCompatActivity {
    private String currentTitle, currentDescription;

    private ImageView backIcon;
    private EditText etTitle, etDescription;
    private Button btnAdd;

    private TextView addTitleTv, addDescriptionTv, addTasktv;
    String titleDataGettingFromIntent;
    String descriptionDataGettingFromIntent;
    int indexDataGettingFromIntent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        // connecting id's
        // we have created this function to make our code little bit clear
        init();
        // handling data from intent
        handleIntentData();
        setDataIfComingFormEditButton();
        initListeners();
    }

    private void init() {
        backIcon = findViewById(R.id.ivBackButton);
        btnAdd = findViewById(R.id.btn_add);
        etTitle = findViewById(R.id.et_title);
        etDescription = findViewById(R.id.et_description);
        addTitleTv = findViewById(R.id.tv_add_title);
        addDescriptionTv = findViewById(R.id.tv_add_description);
        addTasktv = findViewById(R.id.tv_add_task);
    }

    private void handleIntentData() {
        Intent i = getIntent();
        // jis intent ke through aaye hai uska value mil gya
        titleDataGettingFromIntent = i.getStringExtra("title");
        descriptionDataGettingFromIntent = i.getStringExtra("description");
        indexDataGettingFromIntent = i.getIntExtra("index", -1);
        Log.d("toFindIndex", "line no 60: " +indexDataGettingFromIntent);

    }
    private void setDataIfComingFormEditButton() {
        if (titleDataGettingFromIntent != null || descriptionDataGettingFromIntent != null) {
            addTitleTv.setText(R.string.text_edit_title);
            addDescriptionTv.setText(R.string.text_edit_description);
            btnAdd.setText(R.string.text_edit);
            addTasktv.setText(R.string.text_edit_task);
            etTitle.setText(titleDataGettingFromIntent);
            etDescription.setText(descriptionDataGettingFromIntent);
        }
    }

    private void initListeners() {
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTitle = etTitle.getText().toString();
                currentDescription = etDescription.getText().toString();
                if (!currentTitle.isEmpty() || !currentDescription.isEmpty()) {
                    handleAddButtonClick();
                } else {
                    Toast.makeText(CreateTaskActivity.this, "Write something in title or description  ", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
    // item = ["hi", "rishi","sam"]
    // item.add("mango")
    // results = ["hi", "rishi","sam", "mango"]

    // item.set(2,"hate u")
    // the set function takes 2 parameters
    // 1. index(give the index no whose value you want to change)
    // 2. the new value you want to update
    // results = ["hi", "rishi","hate u", "mango"]

    public void handleAddButtonClick() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.title = currentTitle;
        taskEntity.description = currentDescription;

        // whenever index value is not equal to -1 it will go inside if condition
        if (indexDataGettingFromIntent != -1) {
            taskEntity.uid = taskDataFromDb.get(indexDataGettingFromIntent).uid;
            updateTaskData(taskEntity);
            taskDataFromDb.set(indexDataGettingFromIntent, taskEntity);

        } else {
            addTaskData(taskEntity);
        }

        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
        finish();
    }

//        TaskEntity t1 = new TaskEntity();
//       // t1.title = currentTitle;
//       // t1.description = currentDescription;
//        // whenever index value is not equal to -1 it will go inside if condition
//        if (indexDataGettingFromIntent != -1) {
//            updateTaskData(t1);
//           taskDataFromDb.set(indexDataGettingFromIntent, new TaskEntity());
//
//        } else {
//            //taskDetails.add(new TaskDetails(currentTitle, currentDescription));
//            TaskEntity t2 = new TaskEntity();
//            t2.title = currentTitle;
//            t2.description = currentDescription;
//            addTaskData(t2);




    private void addTaskData(TaskEntity taskEntity) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
         db.taskDao().addTaskData(taskEntity);
        Log.d("db", "getAllTaskData: " +  ActivityData.taskDataFromDb);
    }
    private void updateTaskData(TaskEntity taskEntity) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        db.taskDao().updateTaskData(taskEntity);
        Log.d("db", "getAllTaskData: " +  ActivityData.taskDataFromDb);
    }


}