package com.indranugraha.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.Location;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private double latitude, longitude;
    private String title;
    private LatLng currentPosition = new LatLng(-6.830756092280379, 107.13521181713685);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Get data from intent
        latitude = getIntent().getDoubleExtra("latitude", 0);
        longitude = getIntent().getDoubleExtra("longitude", 0);
        title = getIntent().getStringExtra("title");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.main_menu, menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_latest) {
            return true;
        } else if (id == R.id.menu_significant) {
            Intent intent = new Intent(this, ListEarthquakeActivity.class);
            intent.putExtra("type", "significant");
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_felt) {
            Intent intent = new Intent(this, ListEarthquakeActivity.class);
            intent.putExtra("type", "felt");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker at earthquake location
        LatLng quakeLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(quakeLocation)
                .title(title));

        // Move camera to the location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quakeLocation, 8));

        // Calculate distance
        float[] results = new float[1];
        Location.distanceBetween(
                currentPosition.latitude, currentPosition.longitude,
                quakeLocation.latitude, quakeLocation.longitude,
                results
        );
        float distanceInMeters = results[0];
        float distanceInKm = distanceInMeters / 1000;

        mMap.addMarker(new MarkerOptions()
                .position(currentPosition)
                .title("Posisi Anda ke pusat gempa " + String.format("%.2f", distanceInKm) + " km")
                .icon(com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker(
                        com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE
                ))
        );

        mMap.addMarker(new MarkerOptions()
                .position(quakeLocation)
                .title("Pusat Gempa")
                .icon(com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker(
                        com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED
                ))
        );

        mMap.addCircle(new CircleOptions().center(quakeLocation).radius(500000).strokeWidth(2).strokeColor(Color.red(1)));

        Toast.makeText(this, "Jarak Anda ke pusat gempa: " + String.format("%.2f", distanceInKm) + " km", Toast.LENGTH_LONG).show();
    }
}