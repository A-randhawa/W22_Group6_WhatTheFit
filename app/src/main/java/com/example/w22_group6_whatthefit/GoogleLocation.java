package com.example.w22_group6_whatthefit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

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
        //when the button is clicked
        btFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //Get selected position of spinner
                int i = spType.getSelectedItemPosition();
                //Initializing url
            String url = "https://maps.googleapis.com/map/api/place/nearbysearch/json" +//Url
            "?location=" + currLat + "," + currLong + //Location latitude and longitude
            "&radius=10000" + //Nearby radius
             "&types=" + locTypeArr[i] + //Location type
             "&sensor=true" + //Sensor
              "&key=" +getResources().getString(R.string.google_map_key); // google map key =" AIzaSyB_qsP8AOP_P0MdlPz-48TDaJYjTP3vbjo"

                //Execute place task method to download json data
                new PlaceTask().execute(url);


            }

        });
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
                    //get current longitude
                    currLong = location.getLongitude();
                    //Sync map
                    spf.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {

                            //when map is ready
                            map = googleMap;
                            //zoom current location on the map
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(currLat,currLong),10
                            ));

                        }
                    });

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //when permission granted then calling the method for current location
                getCurrentLocation();
            }
        }
    }

    private class PlaceTask extends AsyncTask<String,Integer,String> {
        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {
                //Initialize data
                data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            //Execute parser task
            new ParserTask().execute(s);
        }
    }

    private String downloadUrl(String string) throws IOException {

        //Initialize url
        URL url = new URL(string);
        //Initialize connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //connect
        connection.connect();
        //Initialize input stream
        InputStream stream = connection.getInputStream();
        //Initialize buffer reader
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        //Initialize string builder
        StringBuilder builder = new StringBuilder();
        //Initialize string variable
        String line ="";
        while ((line = reader.readLine()) != null ){
            //append line
            builder.append(line);
        }

        //get append data
        String data = builder.toString();
        //close reader
        reader.close();
        //return data
         return data;
    }

    private class ParserTask extends AsyncTask<String,Integer, List<HashMap<String,String>>>{
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            //create json parser class
            JsonParser jsonParser = new JsonParser();
            //Initialize hashmap list
            List<HashMap<String,String>> mapList = null;
            //Initialize json object
            JSONObject object = null;
            try {
                object = new JSONObject(strings[0]);
                //Parse json object
                mapList = jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //return list

            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            //clear map
            map.clear();
            for(int i =0;i<hashMaps.size(); i++){
                HashMap<String,String> hashMapList = hashMaps.get(i);
                //get latitude
                double lat = Double.parseDouble(hashMapList.get("lat"));
                //get longitude
                double lng = Double.parseDouble(hashMapList.get("lng"));
                //get name
                String name = hashMapList.get("name");
                //Concat latitude and longitude
                LatLng latLng = new LatLng(lat,lng);
                //initialize marker options
                MarkerOptions options = new MarkerOptions();

                //set position
                options.position(latLng);
                //set titile
                options.title(name);
                //add marker on map
                map.addMarker(options);
            }
        }
    }

}