package com.example.appzunsrs;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    EditText ime;
    EditText prezime;
    EditText jmbg;
    EditText email;
    Spinner spiner;
    EditText korIme;
    EditText lozinka;
    EditText pLozinka;
    Ucenik ucenik;
    FirebaseDatabase database;
    DatabaseReference reff;
    Button btnReg;
    //private static final String[] items=new String[]{"ПРВИ", "ДРУГИ", "ТРЕЋИ", "ЧЕТВРТИ", "ПЕТИ", "ШЕСТИ", "СЕДМИ", "ОСМИ", "ДЕВЕТИ"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("РЕГИСТРАЦИЈА");

        ime=(EditText)findViewById(R.id.idIme);
        prezime=(EditText)findViewById(R.id.idPrezime);
        jmbg=(EditText)findViewById(R.id.idJmbg);
        spiner=(Spinner)findViewById(R.id.spinner);
        email=(EditText)findViewById(R.id.idMail);
        korIme=(EditText)findViewById(R.id.idKorIme);
        lozinka=(EditText)findViewById(R.id.idLozinka);
        pLozinka=(EditText)findViewById(R.id.idPLozinka);
        btnReg=(Button)findViewById(R.id.btnRegSe);

        database = FirebaseDatabase.getInstance();
        reff = database.getReference("Ucenici");

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item){

        public View getView(int position, View convertView, ViewGroup parent) {

            View v = super.getView(position, convertView, parent);
            if (position == getCount()) {
                ((TextView)v.findViewById(android.R.id.text1)).setText("");
                ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
            }
            return v;
        }
            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }
    };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("ПРВИ");
        adapter.add("ДРУГИ");
        adapter.add("ТРЕЋИ");
        adapter.add("ЧЕТВРТИ");
        adapter.add("ПЕТИ");
        adapter.add("ШЕСТИ");
        adapter.add("СЕДМИ");
        adapter.add("ОСМИ");
        adapter.add("ДЕВЕТИ");
        adapter.add("ИЗАБЕРТЕ РАЗРЕД");
        spiner.setAdapter(adapter);
        spiner.setSelection(adapter.getCount());
        spiner.setOnItemSelectedListener(this);

        ucenik=new Ucenik();
        reff= FirebaseDatabase.getInstance().getReference().child("Ucenici");


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void registracija(View view){

        //PROVJERE ZA POPUNJENOST
        if (TextUtils.isEmpty(ime.getText())) {
            ime.setError("Име је обавезно!");
        } else if (TextUtils.isEmpty(prezime.getText())) {
            prezime.setError("Презиме је обавезно!");
        } else if (TextUtils.isEmpty(jmbg.getText())) {
            jmbg.setError("ЈМБГ је обавезан!");
        } else if (TextUtils.isEmpty(email.getText())) {
            email.setError("Емаил је обавезан!");
        } else if (TextUtils.isEmpty(korIme.getText())) {
            korIme.setError("Корисничо име је обавезно!");
        } else if (TextUtils.isEmpty(lozinka.getText())) {
            lozinka.setError("Лозинка је обавезна!");
        } else if (TextUtils.isEmpty(pLozinka.getText())) {
            pLozinka.setError("Потврда лозинке је обавезна!");
        } else if (!(spiner.getSelectedItem().equals("ПРВИ") || spiner.getSelectedItem().equals("ДРУГИ") || spiner.getSelectedItem().equals("ТРЕЋИ")
                || spiner.getSelectedItem().equals("ЧЕТВРТИ") || spiner.getSelectedItem().equals("ПЕТИ") || spiner.getSelectedItem().equals("ШЕСТИ")
                || spiner.getSelectedItem().equals("СЕДМИ") || spiner.getSelectedItem().equals("ОСМИ") || spiner.getSelectedItem().equals("ДЕВЕТИ"))) {
            spiner.requestFocus();
            spiner.performClick();
        }else{
            //PROVJERA ZA LOZINKU
            if(!(lozinka.getText().toString().equals(pLozinka.getText().toString()))){
                lozinka.setError("Поново унесите лозинку!");
                pLozinka.setError("У поље лозинка и потврда лозинке морате унијети исту лозинку!");
            }else {

                ucenik.setIme(ime.getText().toString());
                ucenik.setPrezime(prezime.getText().toString());
                ucenik.setJmbg(jmbg.getText().toString());
                ucenik.setKorisnickoIme(korIme.getText().toString());
                ucenik.setLozinka(lozinka.getText().toString());
                ucenik.setMail(email.getText().toString());
                ucenik.setRazred(spiner.getSelectedItem().toString());

                //PRVO PROVJERI DA LI IMA NEKO U BAZI SA TIM JMBG
                //DODA U BAZU


                reff.child(ucenik.getJmbg()).setValue(ucenik);




                Toast.makeText(RegistrationActivity.this, "Успјешно сте се регистровали!", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }

        }
    }
}