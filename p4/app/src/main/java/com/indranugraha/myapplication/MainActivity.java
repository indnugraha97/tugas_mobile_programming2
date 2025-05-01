package com.indranugraha.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
public class MainActivity extends AppCompatActivity {
    private TextView tvTanggal, tvJam, tvMagnitude, tvKedalaman, tvWilayah, tvKoordinat, tvInformasiGempa, tvDirasakan, tvPotensi;
    private ImageView ivShakemap;
    private MapView mapView;
    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        tvInformasiGempa = findViewById(R.id.tvInformasiGempa);
        tvDirasakan = findViewById(R.id.tvDirasakan);
        tvPotensi = findViewById(R.id.tvPotensi);
//        tvTanggal = findViewById(R.id.tvTanggal);
//        tvJam = findViewById(R.id.tvJam);
//        tvMagnitude = findViewById(R.id.tvMagnitude);
//        tvKedalaman = findViewById(R.id.tvKedalaman);
//        tvWilayah = findViewById(R.id.tvWilayah);
//        tvKoordinat = findViewById(R.id.tvKoordinat);
        ivShakemap = findViewById(R.id.ivShakemap);
        mapView = findViewById(R.id.mapView);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // Map siap digunakan
                gMap = googleMap;
                getLatestEarthquake();
            }
        });

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    private void getLatestEarthquake() {
        String url = "https://data.bmkg.go.id/DataMKG/TEWS/autogempa.json";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONObject infogempa = jsonResponse.getJSONObject("Infogempa");
                        JSONObject gempa = infogempa.getJSONObject("gempa");

                        // Parse data
                        String tanggal = gempa.getString("Tanggal");
                        String jam = gempa.getString("Jam");
                        String magnitude = gempa.getString("Magnitude");
                        String kedalaman = gempa.getString("Kedalaman");
                        String wilayah = gempa.getString("Wilayah");
                        String coordinates = gempa.getString("Coordinates");
                        String potensi = gempa.getString("Potensi");
                        String dirasakan = gempa.getString("Dirasakan");
                        String shakemap = gempa.getString("Shakemap");

                        // Update UI
                        tvInformasiGempa.setText(tanggal + ", " + jam + ", " + magnitude + " Magnitudo" + ", kedalaman " + kedalaman);
                        tvPotensi.setText(potensi);
                        tvDirasakan.setText(dirasakan);
//                        tvTanggal.setText("Tanggal: " + tanggal);
//                        tvJam.setText("Jam: " + jam);
//                        tvMagnitude.setText("Magnitude: " + magnitude);
//                        tvKedalaman.setText("Kedalaman: " + kedalaman);
//                        tvWilayah.setText("Wilayah: " + wilayah);
//                        tvKoordinat.setText("Koordinat: " + coordinates);

                        // Load shakemap image
                        String shakemapUrl = "https://data.bmkg.go.id/DataMKG/TEWS/" + shakemap;
                        Picasso.get().load(shakemapUrl).into(ivShakemap);

                        // Update map
                        String[] coordParts = coordinates.split(",");
                        double latitude = Double.parseDouble(coordParts[0].trim());
                        double longitude = Double.parseDouble(coordParts[1].trim());

                        LatLng quakeLocation = new LatLng(latitude, longitude);
                        gMap.addMarker(new MarkerOptions()
                                .position(quakeLocation)
                                .title("Gempa: " + magnitude + " SR - " + wilayah));
                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quakeLocation, 6));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show());

        queue.add(request);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}