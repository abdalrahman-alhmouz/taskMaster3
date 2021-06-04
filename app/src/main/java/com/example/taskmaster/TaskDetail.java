package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        TextView titleTask = TaskDetail.this.findViewById(R.id.taskName);
        TextView bodyTask = TaskDetail.this.findViewById(R.id.loremIpsumTaskDetails);
        TextView stateTask = TaskDetail.this.findViewById(R.id.descriptionTaskDetails);

        titleTask.setText(intent.getExtras().getString("title"));
        bodyTask.setText(intent.getExtras().getString("body"));
        stateTask.setText(intent.getExtras().getString("state"));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent mtIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(mtIntent, 0);
        return true;

    }

}