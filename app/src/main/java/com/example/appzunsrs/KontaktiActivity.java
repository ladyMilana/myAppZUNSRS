package com.example.appzunsrs;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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


    //MOJA LOKACIJA
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    Location loc2;
    float distanca;
    Radnje najblizaRadnja;
    float minDist;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakti);
        setTitle("КОНТАКТ");
        listView = (ListView) findViewById(R.id.idListView);
        database = FirebaseDatabase.getInstance();
        listaRadnji = new ArrayList<>();

        Log.i("CREATE1", "CREATE1");
        //MOJA LOKACIJA
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        //BAZA
        reff = database.getReference("Radnje");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listaRadnji.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Radnje radnja = ds.getValue(Radnje.class);
                    listaRadnji.add(radnja);
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



    private void fetchLastLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);

            return;
        }

        Log.i("FETCH1", "FETCH1");
        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation=location;
                    // Toast.makeText(KontaktiActivity.this, currentLocation.getLatitude()+ " "+currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                    //naci najblizu
                    Log.i("SUCC", "SUCC");
                    loc2=new Location("");

                    najblizaRadnja=new Radnje();
                    minDist=0;

                    reff = database.getReference("Radnje");
                    reff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot ds : snapshot.getChildren()) {
                                Radnje radnja = ds.getValue(Radnje.class);
                              loc2=getLocationFromAddress(radnja.getAdresa().toString());

                                distanca=currentLocation.distanceTo(loc2);
                                if(distanca<minDist){
                                    minDist=distanca;
                                    najblizaRadnja=radnja;
                                }
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_DENIED){

                    fetchLastLocation();
                }
                break;
        }
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

    public void najblizaLokacija(View view){

        Intent intent = new Intent(KontaktiActivity.this, Pop.class);
        intent.putExtra("ADR", najblizaRadnja.getAdresa().toString());
        intent.putExtra("NAZIV", najblizaRadnja.getNaziv().toString());
        intent.putExtra("TEL", najblizaRadnja.getTelefon().toString());
        startActivity(intent);

    }
}