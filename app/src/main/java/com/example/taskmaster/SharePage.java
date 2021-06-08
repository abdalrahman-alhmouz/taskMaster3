package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class SharePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_page);

        Intent intent = getIntent();
        Uri data = intent.getData();

        if (intent.getType().indexOf("image/") != -1) {
        } else if (intent.getType().equals("text/plain")) {
        }
    }
}