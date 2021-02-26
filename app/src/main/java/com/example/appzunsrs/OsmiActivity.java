package com.example.appzunsrs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class OsmiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osmi);
        setTitle("ОСМИ РАЗРЕД");
    }

    public void goToGeo8(View view){

        goToUrl("https://www.zunsrs.com/e-udzbenici/8-razred/Geografija8/");
    }

    private void goToUrl(String url){

        Intent intent = new Intent(OsmiActivity.this, WebPrikazActivity.class);
        intent.putExtra("URL", url);
        startActivity(intent);
    }



}