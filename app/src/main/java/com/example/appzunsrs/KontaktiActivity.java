package com.example.appzunsrs;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KontaktiActivity extends AppCompatActivity {

    DatabaseReference reff;
    FirebaseDatabase database;
    ListView listView;
    List<Radnje> listaRadnji;
    boolean first;

    private LocationManager locationManager;
    private LocationListener locationListener;

    //MOJA LOKACIJA
    Location currentLocation;

    private static final int REQUEST_CODE = 101;
    Location loc2;
    float distanca;
    Radnje najblizaRadnja;
    float minDist;
    

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakti);
        setTitle("КОНТАКТ");
        listView = (ListView) findViewById(R.id.idListView);
        database = FirebaseDatabase.getInstance();
        listaRadnji = new ArrayList<>();
        currentLocation=new Location("");
        loc2=new Location("");
        minDist=0;


        kreiranjeRadnji();
        //dohvatanjeRadnjiIzBaze();


        //MOJA LOKACIJA i kad se promjeni
        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                currentLocation=location;

                najblizaRadnja=new Radnje();
                first =true;

                //dohvatanje iz baze
                /*
                reff = database.getReference("Radnje");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Radnje radnja = ds.getValue(Radnje.class);
                            loc2=new Location("");
                            loc2=getLocationFromAddress(radnja.getAdresa().toString());
                            distanca=currentLocation.distanceTo(loc2);

                            if(first){
                                minDist=distanca;
                                najblizaRadnja.setAdresa(radnja.getAdresa().toString());
                                najblizaRadnja.setNaziv(radnja.getNaziv().toString());
                                najblizaRadnja.setTelefon(radnja.getTelefon().toString());
                                first=false;
                            }else{
                                if(distanca<minDist){
                                    minDist=distanca;
                                    najblizaRadnja.setAdresa(radnja.getAdresa().toString());
                                    najblizaRadnja.setNaziv(radnja.getNaziv().toString());
                                    najblizaRadnja.setTelefon(radnja.getTelefon().toString());
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/


                for (Radnje r : listaRadnji) {

                    loc2=new Location("");
                    loc2=getLocationFromAddress(r.getAdresa().toString());
                    distanca=currentLocation.distanceTo(loc2);

                    if(first){
                        minDist=distanca;
                        najblizaRadnja.setAdresa(r.getAdresa().toString());
                        najblizaRadnja.setNaziv(r.getNaziv().toString());
                        najblizaRadnja.setTelefon(r.getTelefon().toString());
                        first=false;
                    }else{
                        if(distanca<minDist){
                            minDist=distanca;
                            najblizaRadnja.setAdresa(r.getAdresa().toString());
                            najblizaRadnja.setNaziv(r.getNaziv().toString());
                            najblizaRadnja.setTelefon(r.getTelefon().toString());
                        }
                    }
                }
        }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Intent i=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, REQUEST_CODE);
            return;
        }else{

            getLocation();
        }


        //NAJBLIZE RADNJE na pocetku da izracuna
        najblizaRadnja=new Radnje();
        first =true;

       /* reff = database.getReference("Radnje");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Radnje radnja = ds.getValue(Radnje.class);
                    loc2=new Location("");
                    loc2=getLocationFromAddress(radnja.getAdresa().toString());
                    distanca=currentLocation.distanceTo(loc2);

                    if(first){
                        minDist=distanca;
                        najblizaRadnja.setAdresa(radnja.getAdresa().toString());
                        najblizaRadnja.setNaziv(radnja.getNaziv().toString());
                        najblizaRadnja.setTelefon(radnja.getTelefon().toString());
                        first=false;
                    }else{
                        if(distanca<minDist){
                            minDist=distanca;
                            najblizaRadnja.setAdresa(radnja.getAdresa().toString());
                            najblizaRadnja.setNaziv(radnja.getNaziv().toString());
                            najblizaRadnja.setTelefon(radnja.getTelefon().toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

       for (Radnje r : listaRadnji) {

            loc2=new Location("");
            loc2=getLocationFromAddress(r.getAdresa().toString());
            distanca=currentLocation.distanceTo(loc2);

            if(first){
                minDist=distanca;
                najblizaRadnja.setAdresa(r.getAdresa().toString());
                najblizaRadnja.setNaziv(r.getNaziv().toString());
                najblizaRadnja.setTelefon(r.getTelefon().toString());
                first=false;
            }else{
                if(distanca<minDist){
                    minDist=distanca;
                    najblizaRadnja.setAdresa(r.getAdresa().toString());
                    najblizaRadnja.setNaziv(r.getNaziv().toString());
                    najblizaRadnja.setTelefon(r.getTelefon().toString());
                }
            }
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    getLocation();
                return;
        }
    }

    @SuppressLint("MissingPermission")
    public void getLocation(){
        Log.i("LOKACIJA", "UDJE U get location");
        locationManager.requestLocationUpdates("gps", 5000, 30, locationListener);
    }


    public Location getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(this);
        List<Address> address = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (address==null) {
            return null;
        }

        Location l = new Location("");
        l.setLatitude(address.get(0).getLatitude());
        l.setLongitude(address.get(0).getLongitude());
        return l;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)


    public void najblizaLokacija(View view){

        Intent intent = new Intent(KontaktiActivity.this, Pop.class);
        intent.putExtra("ADR", najblizaRadnja.getAdresa().toString());
        intent.putExtra("NAZIV", najblizaRadnja.getNaziv().toString());
        intent.putExtra("TEL", najblizaRadnja.getTelefon().toString());
        startActivity(intent);
    }

    public void kreiranjeRadnji(){

        listaRadnji.clear();

        Radnje  r=new Radnje("ПЦ Приједор (ВП)",
                "Свале бб, Приједор", "052231207");
        listaRadnji.add(r);

        r=new Radnje("Комерцијална служба",
                "Слободана Кустурића 15, Бања Лука", "051436838");
        listaRadnji.add(r);
        r=new Radnje("Књижара Бања Лука 1",
                "Слободана Кустурића 15, Бања Лука", "051212319");
        listaRadnji.add(r);
        r=new Radnje("Књижара Бања Лука 2",
                "Краља Петра I Карађорђевића, Бања Лука", "051212478");
        listaRadnji.add(r);
        r=new Radnje("Књижара Приједор 1",
                "Милоша Обреновића 1, Приједор", "052213088");
        listaRadnji.add(r);
        r=new Radnje("Књижара Приједор 2",
                "Академика Ј. Рашковића 20, Приједор", "052211076");
        listaRadnji.add(r);
        r=new Radnje("Књижара Нови Град",
                "Карађорђа Петровића, Нови Град", "052751455");
        listaRadnji.add(r);
        r=new Radnje("Књижара Бања Лука 3",
                "Симе Матавуља 2, Бања Лука ", "051213023");
        listaRadnji.add(r);
        r=new Radnje("ПЦ Добој (ВП)",
                "Светог Саве 57ц, Добој", "053222541");
        listaRadnji.add(r);
        r=new Radnje("Књижара Добој",
                "Светог Саве 33, Добој", "053221535");
        listaRadnji.add(r);
        r=new Radnje("Књижара Модрича",
                "Трг др Милана Јелића 17, Модрича", "052751455");
        listaRadnji.add(r);
        r=new Radnje("Књижара Котор Варош",
                "КЦара Душана бб, Котор Варош", "051785325");
        listaRadnji.add(r);
        r=new Radnje("ПЦ Требиње (ВП)",
                "Светосавска 4, Требиње", "059272240");
        listaRadnji.add(r);
        r=new Radnje("Књижара Требиње",
                "Светосавска 11,Требиње", "059272241");
        listaRadnji.add(r);
        r=new Radnje("Књижара Билећа",
                "Обилићев вијенац 15, Билећа", "059370799");
        listaRadnji.add(r);
        r=new Radnje("Књижара Гацко",
                "Солунских добровољаца 12, Гацко", "059473302");
        listaRadnji.add(r);
        r=new Radnje("Књижара Невесиње",
                "Немањића бб, Невесиње", "059602676");
        listaRadnji.add(r);
        r=new Radnje("ПЦ Бијељина (ВП)",
                "Сремска бб, Бијељина", "055201506");
        listaRadnji.add(r);
        r=new Radnje("Књижара Бијељина 1 ",
                "Гаврила Принципа 14, Бијељина", "055206409");
        listaRadnji.add(r);
        r=new Radnje("Књижара Бијељина 2 ",
                "Рачанска 1д, Бијељина", "055202056");
        listaRadnji.add(r);
        r=new Radnje("Књижара Зворник",
                "Филипа Кљајића, Зворник", "056211299");
        listaRadnji.add(r);
        r=new Radnje("Књижара Братунац",
                "Трг Милоша Обилића бб, Братунац", "056410203");
        listaRadnji.add(r);
        r=new Radnje("ПЦ Источно Сарајево (ВП)",
                "Зорана Цвијетића 58, Источно Сарајево", "057342886");
        listaRadnji.add(r);
        r=new Radnje("Књижара Источно Сарајево",
                "Зорана Цвијетића 58, Источно Сарајево", "057340058");
        listaRadnji.add(r);
        r=new Radnje("Књижара Вишеград",
                "Андрићград 22, Вишеград", "058620730");
        listaRadnji.add(r);
        r=new Radnje("Књижара Фоча",
                "Шантићева 16, Фоча", "058232458");
        listaRadnji.add(r);


        ListAdapter adapter = new ListAdapter(KontaktiActivity.this, listaRadnji);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //proslijedi adresu
                Intent intent = new Intent(KontaktiActivity.this, Pop.class);
                intent.putExtra("ADR", listaRadnji.get(position).getAdresa().toString());
                intent.putExtra("NAZIV", listaRadnji.get(position).getNaziv().toString());
                intent.putExtra("TEL", listaRadnji.get(position).getTelefon().toString());
                startActivity(intent);

            }
        });
    }

    public void dohvatanjeRadnjiIzBaze(){

        //DOHVATANJE KONTAKATA IZ BAZE ZA PRIKAZ
        reff = database.getReference("Radnje");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listaRadnji.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Radnje radnja = ds.getValue(Radnje.class);
                    listaRadnji.add(radnja);
                    loc2=getLocationFromAddress(radnja.getAdresa().toString());
                }

                ListAdapter adapter = new ListAdapter(KontaktiActivity.this, listaRadnji);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        //proslijedi adresu
                        Intent intent = new Intent(KontaktiActivity.this, Pop.class);
                        intent.putExtra("ADR", listaRadnji.get(position).getAdresa().toString());
                        intent.putExtra("NAZIV", listaRadnji.get(position).getNaziv().toString());
                        intent.putExtra("TEL", listaRadnji.get(position).getTelefon().toString());
                        startActivity(intent);

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}