package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferenceEditor=sharedPref.edit();

        Button saveButton=findViewById(R.id.button5);

        Context context = getApplicationContext();
        CharSequence text = "saved";
        int duration = Toast.LENGTH_SHORT;

        final Toast toast = Toast.makeText(context, text, duration);

        saveButton.setOnClickListener((view)->{

            toast.show();
            EditText userName=findViewById(R.id.editTextTextPersonName3);
            String name=userName.getText().toString();
            preferenceEditor.putString("userName",name) ;
            preferenceEditor.apply();




            Intent intent=new Intent(SettingPage.this,MainActivity.class);
            startActivity(intent);


        });

    }

}