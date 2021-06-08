package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.renderscript.RenderScript;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextClassification;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements ContactAdapter.OnInteractingWithTaskListener {
    private FusedLocationProviderClient fusedLocationClient;

    Database database;

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        TextView textView = findViewById(R.id.myTaskTitle);
        String user = sharedPref.getString("userName", "user");
        textView.setText(user);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // lab 38 __________________

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();

                        Log.d("notifications:", token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();

                    }
                });

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
                    String status = null;
                    String description = null;
                    String title = null;
                    String location = null;
                    while (todos.hasNext()) {
                        Task todo = todos.next();

                        Log.i("Tutorial", "==== Todo ====");
                        Log.i("Tutorial", "Name: " + todo.getTitle());
                        title = todo.getTitle();

                        if (todo.getStatus() != null) {
                            Log.i("Tutorial", "Priority: " + todo.getStatus().toString());
                            status = todo.getStatus();

                        }


                        if (todo.getDescription() != null) {
                            Log.i("Tutorial", "Description: " + todo.getDescription());
                            description = todo.getDescription();
                            System.out.println(description + "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");

                        }
                        if (todo != null) {
                            Log.i("Tutorial", "Description: " + todo.getDescription());
                            description = todo.getDescription();
                            System.out.println(description + "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");

                        }
                        TaskTable taskTableSaved = new TaskTable(title, status, description);
                        taskTables.add(taskTableSaved);
                        System.out.println(title + "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
                        System.out.println(status + "sssssssssssssssssssssssssssssssssssssssssss");
                        System.out.println(description + "sssssssssssssssssssssssssssssssssssssss");
                    }
                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
        );


        RecyclerView recyclerView = findViewById(R.id.taskRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        System.out.println(taskTables + "sssssssssssssssssssssssssssssssssssssss");
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

        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},2);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.i("lactation service  ","latitude is"+location.getLatitude()+"longitude is"+location.getLongitude());
                            Geocoder geocoder=new Geocoder(MainActivity.this, Locale.getDefault());

                            try {
                                List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),5);
                                Log.i( "dd",addresses.get(0).toString());
                                System.out.println(addresses.get(0).toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
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