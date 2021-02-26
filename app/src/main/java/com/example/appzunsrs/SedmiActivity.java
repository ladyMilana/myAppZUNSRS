package com.example.appzunsrs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class SedmiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sedmi);
        setTitle("СЕДМИ РАЗРЕД");
    }

    public void goToGeo7(View view){

        goToUrl("https://www.zunsrs.com/e-udzbenici/7-razred/Geo-7/NewFolder/");
    }

    private void goToUrl(String url){

        Intent intent = new Intent(SedmiActivity.this, WebPrikazActivity.class);
        intent.putExtra("URL", url);
        startActivity(intent);
    }

}