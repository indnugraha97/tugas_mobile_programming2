package com.indranugraha.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListEarthquakeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EarthquakeAdapter adapter;
    private List<Earthquake> earthquakeList = new ArrayList<>();
    private String earthquakeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_earthquake);

        earthquakeType = getIntent().getStringExtra("type");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EarthquakeAdapter(earthquakeList, this);
        recyclerView.setAdapter(adapter);

        getEarthquakeData();
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

    private void getEarthquakeData() {
        String url;
        if (earthquakeType.equals("significant")) {
            url = "https://data.bmkg.go.id/DataMKG/TEWS/gempaterkini.json";
        } else {
            url = "https://data.bmkg.go.id/DataMKG/TEWS/gempadirasakan.json";
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONObject infogempa = jsonResponse.getJSONObject("Infogempa");
                        JSONArray gempaArray = infogempa.getJSONArray("gempa");

                        earthquakeList.clear();
                        for (int i = 0; i < gempaArray.length(); i++) {
                            JSONObject gempa = gempaArray.getJSONObject(i);
                            Earthquake earthquake = new Earthquake();

                            earthquake.setTanggal(gempa.getString("Tanggal"));
                            earthquake.setJam(gempa.getString("Jam"));
                            earthquake.setMagnitude(gempa.getString("Magnitude"));
                            earthquake.setKedalaman(gempa.getString("Kedalaman"));
                            earthquake.setWilayah(gempa.getString("Wilayah"));

                            if (earthquakeType.equals("felt")) {
                                earthquake.setDirasakan(gempa.getString("Dirasakan"));
                            }

                            String coordinates = gempa.getString("Coordinates");
                            String[] coordParts = coordinates.split(",");
                            earthquake.setLatitude(Double.parseDouble(coordParts[0].trim()));
                            earthquake.setLongitude(Double.parseDouble(coordParts[1].trim()));

                            earthquakeList.add(earthquake);
                        }

                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show());

        queue.add(request);
    }
}