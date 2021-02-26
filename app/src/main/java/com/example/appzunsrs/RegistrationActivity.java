package com.example.appzunsrs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {


    EditText ime;
    EditText prezime;
    EditText jmbg;
    EditText korIme;
    EditText lozinka;
    EditText pLozinka;
    Ucenik ucenik;
    DatabaseReference reff;
    Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("РЕГИСТРАЦИЈА");

        ime=(EditText)findViewById(R.id.idIme);
        prezime=(EditText)findViewById(R.id.idPrezime);
        jmbg=(EditText)findViewById(R.id.idJmbg);
        korIme=(EditText)findViewById(R.id.idKorIme);
        lozinka=(EditText)findViewById(R.id.idLozinka);
        pLozinka=(EditText)findViewById(R.id.idPLozinka);

        btnReg=(Button)findViewById(R.id.btnRegSe);
        ucenik=new Ucenik();
        reff= FirebaseDatabase.getInstance().getReference().child("Ucenik");

        btnReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                ucenik.setIme(ime.toString());
                ucenik.setPrezime(prezime.toString());
                ucenik.setJmbg(jmbg.toString());
                ucenik.setKorisnickoIme(korIme.toString());
                ucenik.setLozinka(lozinka.toString());

            }
        });
    }
}