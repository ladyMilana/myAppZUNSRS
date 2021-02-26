package com.example.appzunsrs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class DevetiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deveti);
        setTitle("ДЕВЕТИ РАЗРЕД");
    }

    public void goToGeo9(View view){

        goToUrl("https://www.zunsrs.com/e-udzbenici/9-razred/Geografija-9/");
    }

    private void goToUrl(String url){

        Intent intent = new Intent(DevetiActivity.this, WebPrikazActivity.class);
        intent.putExtra("URL", url);
        startActivity(intent);

    }

}