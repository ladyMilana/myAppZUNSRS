package com.example.appzunsrs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SviUdzbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svi_udzb);

        setTitle("СВИ УЏБЕНИЦИ");
    }


    public void goToPrvi(View view){
        Intent intent=new Intent(SviUdzbActivity.this, PrviActivity.class);
        startActivity(intent);
    }

    public void goToDrugi(View view){
        Intent intent=new Intent(SviUdzbActivity.this, DrugiActivity.class);
        startActivity(intent);
    }

    public void goToTreci(View view){
        Intent intent=new Intent(SviUdzbActivity.this, TreciActivity.class);
        startActivity(intent);
    }

    public void goToCetvrti(View view){
        Intent intent=new Intent(SviUdzbActivity.this, CetvrtiActivity.class);
        startActivity(intent);
    }

    public void goToPeti(View view){
        Intent intent=new Intent(SviUdzbActivity.this, PetiActivity.class);
        startActivity(intent);
    }

    public void goToSesti(View view){
        Intent intent=new Intent(SviUdzbActivity.this, SestiActivity.class);
        startActivity(intent);
    }

    public void goToSedmi(View view){
        Intent intent=new Intent(SviUdzbActivity.this, SedmiActivity.class);
        startActivity(intent);
    }

    public void goToOsmi(View view){
        Intent intent=new Intent(SviUdzbActivity.this, OsmiActivity.class);
        startActivity(intent);
    }

    public void goToDeveti(View view){
        Intent intent=new Intent(SviUdzbActivity.this, DevetiActivity.class);
        startActivity(intent);
    }
}