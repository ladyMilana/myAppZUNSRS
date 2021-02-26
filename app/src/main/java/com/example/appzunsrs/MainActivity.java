package com.example.appzunsrs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("ДОБРОДОШЛИ");

    }

    public void login(View view){

        Intent intent=new Intent(MainActivity.this, HomePageActivity.class);
        startActivity(intent);
    }

    public void registration(View view){

        Intent intent=new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

}