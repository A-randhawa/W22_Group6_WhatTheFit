package com.example.w22_group6_whatthefit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class GoogleLocation extends AppCompatActivity {

    //Initialize variables
    Spinner spType;
    Button btFind;
    SupportMapFragment spf;
    GoogleMap map;

    FusedLocationProviderClient fusedLocationProviderClient;

    //current longitude and latitude
    double currLat = 0, currLong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_location);

        //assigning variable
        spType = findViewById(R.id.spinner_type);
        btFind = findViewById(R.id.searchBtn);
        spf = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleFrag);

        //Initializing an array for location type
        String[] locTypeArr = {"gyms", "restaurants", "personal trainer"};

        //Initializing array for location names
        String[] locNameArr = {"Gyms", "Healthy Restaurants", "Personal Trainer"};

        //setting adapter for spinner
        spType.setAdapter(new ArrayAdapter<>(GoogleLocation.this,
                android.R.layout.simple_spinner_dropdown_item, locNameArr));

        //Initialize fused location provider client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            //when permission is granted call the method for current location
            getCurrentLocation();
        }
        else{
            //when permission denied request for permission
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},44
            );
        }
    }

    //method to get current location
    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                //when there is a success
                if(location != null){
                    //when location is not equal to null
                    //get current latitude
                    currLat = location.getLatitude();
                    //Sync map
                    spf.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            //when map is ready
                            map = googleMap;
                            //zoom current location on map
                            map.animateCamera(CameraUpdateFactory.
                                    newLatLngZoom( new LatLng(currLat,currLong),10));
                        }
                    });

                }
            }
        });
    }


}