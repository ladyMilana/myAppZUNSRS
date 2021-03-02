package com.example.appzunsrs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class TreciActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treci);
        setTitle("ТРЕЋИ РАЗРЕД");
    }

    public void goToSrpskiJ3(View view){

        goToUrl("https://www.zunsrs.com/e-udzbenici/3-razred/Srpski-jezik-i-jezicka-kultura-3/");
    }

    private void goToUrl(String url){

        Intent intent = new Intent(TreciActivity.this, WebPrikazActivity.class);
        intent.putExtra("URL", url);
        startActivity(intent);
    }


}