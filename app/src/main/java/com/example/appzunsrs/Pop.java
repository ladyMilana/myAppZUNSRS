package com.example.appzunsrs;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class Pop extends FragmentActivity implements OnMapReadyCallback {

    Location loc;
    String adr;
    String naziv;
    String tel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);

        //dohvati adresu
        adr = getIntent().getStringExtra("ADR");
        naziv = getIntent().getStringExtra("NAZIV");
        tel = getIntent().getStringExtra("TEL");


        TextView naz = (TextView) findViewById(R.id.idNazivKnjizare);
        FloatingActionButton buttonCall = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        naz.setText(naziv);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.8));

        loc=new Location("");
        loc = getLocationFromAddress(adr);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(Pop.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng latLng=new LatLng(loc.getLatitude(), loc.getLongitude());
        MarkerOptions markerOptions=new MarkerOptions().position(latLng).title(adr);
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
        googleMap.addMarker(markerOptions);
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

    public void callNumber(View view){
        Intent intent=new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+tel));
        startActivity(intent);
    }


}
