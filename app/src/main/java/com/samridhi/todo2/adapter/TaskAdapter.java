package com.samridhi.todo2.adapter;

import static com.samridhi.todo2.adapter.ActivityData.taskDataFromDb;
import static com.samridhi.todo2.adapter.ActivityData.taskDetails;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samridhi.todo2.R;
import com.samridhi.todo2.data.local.database.AppDatabase;
import com.samridhi.todo2.data.local.entity.TaskEntity;

import java.util.ArrayList;
import java.util.List;
// Step 3- we will create the adapter class


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MainViewHolder> {
    public static final String TAG = "TaskAdapter";
    public static String fan = "hi";

     //private ArrayList<TaskDetails> itemList;
     private List<TaskEntity> itemList = new ArrayList<>();
     private Context context;
    private EditTask editTask;



    private String hen;


    public TaskAdapter(Context context, List<TaskEntity> list, EditTask editTask) {
       //itemList = new ArrayList<>();
        this.context = context;
        itemList.addAll(list);
        this.editTask = editTask;

    }
    public void setFilteredList(List<TaskEntity> list) {
        Log.d(TAG, "line no 40: "+ list);
        itemList.clear();
        if(list.isEmpty() ){
            Log.d(TAG, "line no 43: "+ taskDetails);
            Log.d(TAG, "setFilteredList: " + taskDataFromDb);
            itemList.addAll(taskDataFromDb);
        }
        else{
            Log.d(TAG, "line no 47: "+ list);
            itemList.addAll(list);
        }
        for(TaskEntity a : itemList){
            Log.d(TAG, "setFilteredList: "+ a.title);
            Log.d(TAG, "setFilteredList: "+ a.description);
        }
    }
    @NonNull
    @Override
    public TaskAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        MainViewHolder mainViewHolder = new MainViewHolder(view, editTask);
       // Log.d("TaskAdapter", "onCreateViewHolder: line number 33");
        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.MainViewHolder holder, int position) {
        holder.title.setText(itemList.get(position).title);
        holder.description.setText(itemList.get(position).description);
       // Log.d("TaskAdapter", "onBindViewHolder: line number 41 " + position);

    }

    @Override
    public int getItemCount() {
        //Log.d("TaskAdapter", "getItemCount: line number 47");
        return itemList.size();

    }

    public void onDeleteTask(int position) {
        TaskEntity taskToDelete = itemList.get(position);
       // itemList.remove(position);
        taskDataFromDb.remove(taskToDelete);
        notifyItemRemoved(position);
        deleteTaskData(taskToDelete);
    }
    private void deleteTaskData(TaskEntity taskEntity) {
        AppDatabase db = AppDatabase.getDbInstance(context);
        db.taskDao().deleteTaskData(taskEntity);
        Log.d("db", "Deleted task from database.");
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;

        private Button btnEdit;

        private Button btnDelete;

        public MainViewHolder(@NonNull View itemView, EditTask editTask) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("onClickTask", " line number 67 ");
                    Log.d("onClickTask", " line number 68 " + getAdapterPosition());
                    Log.d("onClickTask", " line number 69 " + taskDataFromDb.get(getAdapterPosition()));
                    // here we are calling the function
                    editTask.onEditButtonClick(taskDataFromDb.get(getAdapterPosition()), getAdapterPosition());
                    // getAdpaterPositon -> current index ka value return karta hai
                }
            });

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("delete", "onClick: "+ getAdapterPosition());
                        Log.d("delete", "onClick: "+ itemList.size());
                        onDeleteTask(getAdapterPosition());
                    }
                });


        }
    }


    public interface EditTask {

        // here we are declaring the function
        void onEditButtonClick(TaskEntity taskDataFromDb, int index);
    }


}

