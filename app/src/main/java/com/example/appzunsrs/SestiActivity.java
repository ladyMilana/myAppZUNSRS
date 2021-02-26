package com.example.appzunsrs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class SestiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesti);
        setTitle("ШЕСТИ РАЗРЕД");
    }

    public void goToGeo6(View view){

        goToUrl("https://www.zunsrs.com/e-udzbenici/6-razred/geografija-6/");
    }

    public void goToIst6(View view){

        goToUrl("https://www.zunsrs.com/e-udzbenici/6-razred/istorija-6/");
    }

    private void goToUrl(String url){

        Intent intent = new Intent(SestiActivity.this, WebPrikazActivity.class);
        intent.putExtra("URL", url);
        startActivity(intent);
    }



}