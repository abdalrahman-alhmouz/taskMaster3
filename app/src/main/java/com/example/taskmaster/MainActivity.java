package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.renderscript.RenderScript;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ContactAdapter.OnInteractingWithTaskListener {

    Database database;
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        TextView textView=findViewById(R.id.myTaskTitle);
        String user=sharedPref.getString("userName","user");
        textView.setText(user);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        database = Room.databaseBuilder(getApplicationContext(), Database.class, "tasks")
//                .allowMainThreadQueries()
//                .build();

        Button addTaskButton = MainActivity.this.findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddTask.class);
                startActivity(i);
            }
        });

        ArrayList<TaskTable> taskTables = new ArrayList<>();

        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());

            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("Tutorial", "Could not initialize Amplify", e);
        }

        Amplify.DataStore.query(Task.class,
                todos -> {
                    String status=null ;
                    String description=null ;
                    String title=null ;
                    while (todos.hasNext()) {
                        Task todo = todos.next();

                        Log.i("Tutorial", "==== Todo ====");
                        Log.i("Tutorial", "Name: " + todo.getTitle());
                         title =todo.getTitle();

                        if (todo.getStatus() != null) {
                            Log.i("Tutorial", "Priority: " + todo.getStatus().toString());
                             status =todo.getStatus();

                        }


                        if (todo.getDescription() != null) {
                            Log.i("Tutorial", "Description: " + todo.getDescription());
                             description =todo.getDescription();
                            System.out.println(description+"sssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");

                        }
                        TaskTable taskTableSaved=new TaskTable(title,status,description) ;
                        taskTables.add(taskTableSaved);
                        System.out.println(title+"sssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
                        System.out.println(status+"sssssssssssssssssssssssssssssssssssssssssss");
                        System.out.println(description+"sssssssssssssssssssssssssssssssssssssss");
                    }
                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
        );


        RecyclerView recyclerView = findViewById(R.id.taskRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        System.out.println(taskTables+"sssssssssssssssssssssssssssssssssssssss");
        recyclerView.setAdapter(new ContactAdapter(taskTables, this));

        Button addSettingsButton = MainActivity.this.findViewById(R.id.settingsButtonHome);
        addSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SettingPage.class);
                startActivity(i);
            }
        });

        Button allTasksButton = MainActivity.this.findViewById(R.id.allTasksButton);
        allTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AllTasks.class);
                startActivity(i);
            }
        });


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor preferenceEditor = preferences.edit();

    }


    @Override
    public void taskListener(TaskTable taskTable) {
        Intent intent = new Intent(MainActivity.this, TaskDetail.class);
        intent.putExtra("title", taskTable.title);
        intent.putExtra("body", taskTable.body);
        intent.putExtra("state", taskTable.state);
        this.startActivity(intent);

    }
}