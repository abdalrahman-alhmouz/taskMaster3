package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;

public class AddTask extends AppCompatActivity implements ContactAdapter.OnInteractingWithTaskListener{

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

//        database = Room.databaseBuilder(getApplicationContext(), Database.class, "tasks")
//                .allowMainThreadQueries()
//                .build();

        final EditText taskTitleTV = findViewById(R.id.editTextMyTask);
        final EditText taskDescriptionTV = findViewById(R.id.editTextDoSomething);
        final EditText statusAddTask = findViewById(R.id.editTextStatus);

        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());

            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("Tutorial", "Could not initialize Amplify", e);
        }

        Task taskTable = Task.builder()
                .title(taskTitleTV.getText().toString())
                .status(statusAddTask.getText().toString())
                .description(taskDescriptionTV.getText().toString())
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Context context = getApplicationContext();
        CharSequence text = "Submitted!";
        int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(context, text, duration);

        Button addTaskButton = AddTask.this.findViewById(R.id.buttonAddTaskSubmit);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast.show();
                Amplify.DataStore.save(taskTable,
                        success -> Log.i("Tutorial", "Saved item: " + success.item().getTitle()),
                        error -> Log.e("Tutorial", "Could not save item to DataStore", error)
                );

//            TaskTable newTaskTable = new TaskTable(taskTitleTV.getText().toString(), taskDescriptionTV.getText().toString(), statusAddTask.getText().toString());
//            database.taskDao().saveTask(newTaskTable);
            Intent goToMainActivity = new Intent(AddTask.this, MainActivity.class);
            AddTask.this.startActivity(goToMainActivity);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent mtIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(mtIntent, 0);
        return true;

    }

    @Override
    public void taskListener(TaskTable taskTable) {

    }
}
