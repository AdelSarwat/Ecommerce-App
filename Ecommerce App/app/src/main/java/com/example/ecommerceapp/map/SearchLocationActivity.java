package com.example.ecommerceapp.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.fragment.ShoppingCardFragment;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SearchLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;
    SearchView searchView;
    SupportMapFragment supportMapFragment;
    Button getLocation;
    public static LatLng loc;
    FloatingActionButton btn_GetCurrentLocation;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        searchView = findViewById(R.id.location);
        getLocation = findViewById(R.id.btn_get_location);
        btn_GetCurrentLocation = findViewById(R.id.btn_current_loc);

        //
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                if (location != null || location.isEmpty()) {
                    Geocoder geocoder = new Geocoder(SearchLocationActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    map.clear();
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    loc = latLng;
                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        supportMapFragment.getMapAsync(this);


        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loc == null) {
                    Toast.makeText(SearchLocationActivity.this, "Please Choose Location", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }

            }
        });


        btn_GetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(SearchLocationActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    GetCurrentLocation();
                } else {
                    ActivityCompat.requestPermissions(SearchLocationActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }

            }
        });


    }

    private void GetCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SearchLocationActivity.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            return;
        }
        else {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(SearchLocationActivity.this, Locale.getDefault());
                        try {
                            List<Address> addList = geocoder.getFromLocation(
                                    location.getLatitude(),
                                    location.getLongitude(), 1
                            );
                            map.clear();
                            Address address = addList.get(0);
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                            loc = latLng;
                            map.addMarker(new MarkerOptions().position(latLng).title(addList.get(0).toString()));
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map= googleMap;
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions options = new MarkerOptions();
                loc =latLng;
                options.position(latLng);
                options.title(latLng.latitude+" : "+latLng.longitude);
                map.clear();
               // map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                map.addMarker(options);
            }
        });
    }



}