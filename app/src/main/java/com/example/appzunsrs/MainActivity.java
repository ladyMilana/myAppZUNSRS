package com.example.appzunsrs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reff;
    EditText korIme;
    EditText lozinka;
    TextView poruka;

    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("ДОБРОДОШЛИ");

        database = FirebaseDatabase.getInstance();
        reff = database.getReference("Ucenici");

        korIme=(EditText)findViewById(R.id.username);
        lozinka=(EditText)findViewById(R.id.password);
        poruka=(TextView)findViewById(R.id.poruka);


    }

    @Override
    protected void onStart() {
        super.onStart();
        poruka.setText("");

    }

    public void login(View view){

        poruka.setText("");

        if(TextUtils.isEmpty(korIme.getText())){
            korIme.setError("Корисничко име је обавезно!");
            return;
        }

        if(TextUtils.isEmpty(lozinka.getText())){
            lozinka.setError("Лозинка је обавезна!");
            return;
        }

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Ucenik ucenik=new Ucenik();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ucenik = ds.getValue(Ucenik.class);

                    if((ucenik.getKorisnickoIme().equals(korIme.getText().toString())) && (ucenik.getLozinka().equals(lozinka.getText().toString()))){

                        Intent intent=new Intent(MainActivity.this, HomePageActivity.class);
                        intent.putExtra("RAZRED", ucenik.getRazred());
                        startActivity(intent);
                        poruka.setText("");
                        return;
                    }
                }

                if(TextUtils.isEmpty(korIme.getText()) && TextUtils.isEmpty(lozinka.getText())){
                    poruka.setText("");
                }else {
                    poruka.setText("Неисправна комбинација корисничког имена и лозинке!");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("GRESKA", "Неуспјешно читање из базе!", error.toException());
            }
        });







    }

    public void registration(View view){

        Intent intent=new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

}