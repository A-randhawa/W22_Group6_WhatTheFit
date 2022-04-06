package com.example.w22_group6_whatthefit;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLocationPermission();


    }

    //map ready mehtod
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //toast to test map is ready
        Toast.makeText(this, "Welcome to Google Maps, You can now see your nearest Gyms and Restaurants", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: Map is ready here");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            // Add a marker in Sydney and move the camera
            LatLng anytimeFitness = new LatLng(49.267579, -123.113119);
            mMap.addMarker(new MarkerOptions().position(anytimeFitness).title("Anytime Fitness"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(anytimeFitness));
            LatLng fitnessWorld = new LatLng( 49.1913, -122.8490);
            mMap.addMarker(new MarkerOptions().position(fitnessWorld).title("Fitness World"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(fitnessWorld));


        }
        //permissions for the location to be marked where the device is at
       // if (ActivityCompat.checkSelfPermission(this,
          //      Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
              //  this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
       // }
        //mMap.setMyLocationEnabled(true);
       // mMap.getUiSettings().setMyLocationButtonEnabled(false);//false because we need to add search bar instead

        //when permission is granted initialize it
        //init();


    }

    private static final String TAG = "MapsActivity";
    //global variables
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String Course_Location = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static float DEFAULT_ZOOM = 15f;

    //variables used
    private GoogleMap mMap;
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;//object for device location

    //widgets
    private EditText mSearchText;



    //init method
    private void init(){
        Log.d(TAG,"inti: Initializing");
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                    //execute method for searching
                    geoLocate();
                }

                return false;
            }
        });
    }


    private void geoLocate(){
        Log.d(TAG,"geoLocate: GEO LOCATING");
        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString,1);
        }catch(IOException e){
            Log.e(TAG,"geoLocate: IOEXCEPTION: "+ e.getMessage());
        }
        if(list.size() >0){
            Address address = list.get(0);//coz only 1 location we have
            Log.d(TAG,"geoLocation: found a location: "+address.toString());

            Toast.makeText(this,address.toString(),Toast.LENGTH_SHORT).show();
        }

    }
    //GETTING THE DEVICE CURRENT LOCATION
    private void getDeviceLocation() {
        Log.d(TAG,"get device location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocationPermissionsGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "OnCompletion:  found location!");
                            Location currentLocation = (Location) task.getResult();

                            //we can take that result and move the camer to there in the Movecamera methos
                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),DEFAULT_ZOOM);


                        }else{
                            Log.d(TAG, "oncompletion : not found");
                            Toast.makeText(MapsActivity.this, "Current location not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }
        catch(SecurityException e){
            Log.e(TAG,"getDeviceLocation: Security Exception " + e.getMessage());
        }
    }


    //get camera method
    private void moveCamera(LatLng latLng,float zoom){
        Log.d(TAG,"moveCamera:  moving the camera to: lat: " +latLng.latitude +"lng: "+ latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }


    private void initMap(){
        Log.d(TAG,"intialzing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapsActivity.this);

    }

    /**
     * @Override public void onMapReady(GoogleMap googleMap) {
     * mMap = googleMap;
     * <p>
     * // Add a marker in Sydney and move the camera
     * LatLng vancouver = new LatLng(49.246292, -123.116226);
     * mMap.addMarker(new MarkerOptions().position(vancouver).title("Marker in Vancouver"));
     * mMap.moveCamera(CameraUpdateFactory.newLatLng(vancouver));
     * <p>
     * }
     */

    //LOCATION PERMISSION METHOD: to ask for the permission request
    private void getLocationPermission() {
        Log.d(TAG,"getLocationPermission:  getting location  permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.ACCESS_COARSE_LOCATION};//we need to check these explicitly

        //looping to check each location permission
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    Course_Location) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            }else {
                ActivityCompat.requestPermissions(this,
                        permissions, LOCATION_PERMISSION_REQUEST_CODE);//creating a request code doesnt matter that much to recieve a result
            }
        }else {
            ActivityCompat.requestPermissions(this,
                    permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    //ON REQUEST PERMISSION RESULTS


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG,"onRequestPermissionsResult: it was called");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length>0){
                    for(int i = 0 ; i< grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG,"onRequestPermissionsResult: permissiom failed");
                            return;
                        }
                    }
                    Log.d(TAG,"onRequestPermissionsResult: permission guranteed");
                    mLocationPermissionsGranted = true;
                    //if permissions are granted then initialize map
                    initMap();
                }
            }

        }
    }





}
