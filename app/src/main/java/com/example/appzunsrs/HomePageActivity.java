package com.example.appzunsrs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class HomePageActivity extends AppCompatActivity {

    Button btScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setTitle("ПОЧЕТНИ ЕКРАН - ЗУНСРС");

        btScan=findViewById(R.id.btnScan);
        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                IntentIntegrator intentIntegrator=new IntentIntegrator(
                        HomePageActivity.this
                );

                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult.getContents()!=null){
            //ode na taj link
            Uri uriUrl= Uri.parse(intentResult.getContents());
            Intent launchBrowser=new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        }else{
            Toast.makeText(getApplicationContext(), "Нисте скенирали код!", Toast.LENGTH_LONG).show();
        }
    }

    public void goToSviU(View view){

        Intent intent=new Intent(HomePageActivity.this, SviUdzbActivity.class);
        startActivity(intent);
    }


    public void goToMojiU(View view){

        Intent intent=new Intent(HomePageActivity.this, SestiActivity.class);
        startActivity(intent);
    }

    public void goToContacts(View view){

        Intent intent=new Intent(HomePageActivity.this, KontaktiActivity.class);
        startActivity(intent);
    }


}
