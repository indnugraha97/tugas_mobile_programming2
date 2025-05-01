package com.indranugraha.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indranugraha.myapplication.CeritaAdapter;
import com.indranugraha.myapplication.Cerita;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class ListCeritaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Cerita> ceritaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cerita);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        loadCerita();
    }

    private void loadCerita() {
        String json = loadJSONFromAsset();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Cerita>>() {}.getType();
        ceritaList = gson.fromJson(json, listType);

        CeritaAdapter adapter = new CeritaAdapter(this, ceritaList);
        recyclerView.setAdapter(adapter);
    }

    private String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("cerita.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
