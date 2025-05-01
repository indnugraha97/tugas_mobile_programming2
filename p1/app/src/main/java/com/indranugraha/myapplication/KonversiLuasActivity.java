package com.indranugraha.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class KonversiLuasActivity extends AppCompatActivity {

    private EditText etInputLuas;
    private Spinner spinnerDari, spinnerKe;
    private Button btnKonversi, btnKembali;
    private TextView tvHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konversi_luas);

        etInputLuas = findViewById(R.id.etInputLuas);
        spinnerDari = findViewById(R.id.spinnerDari);
        spinnerKe = findViewById(R.id.spinnerKe);
        btnKonversi = findViewById(R.id.btnKonversi);
        btnKembali = findViewById(R.id.btnKembali);
        tvHasil = findViewById(R.id.tvHasil);

        // Setup spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.luas_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDari.setAdapter(adapter);
        spinnerKe.setAdapter(adapter);

        // Button konversi click listener
        btnKonversi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                konversiLuas();
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void konversiLuas() {
        String inputStr = etInputLuas.getText().toString();

        if (inputStr.isEmpty()) {
            Toast.makeText(this, "Masukkan nilai luas terlebih dahulu", Toast.LENGTH_SHORT).show();
            return;
        }

        double input = Double.parseDouble(inputStr);
        String dariUnit = spinnerDari.getSelectedItem().toString();
        String keUnit = spinnerKe.getSelectedItem().toString();

        double hasil = 0;

        // Konversi ke meter persegi dulu
        double inMeterPersegi = 0;
        switch (dariUnit) {
            case "Kilometer Persegi":
                inMeterPersegi = input * 1000000;
                break;
            case "Hektare":
                inMeterPersegi = input * 10000;
                break;
            case "Meter Persegi":
                inMeterPersegi = input;
                break;
            case "Sentimeter Persegi":
                inMeterPersegi = input / 10000;
                break;
            case "Mil Persegi":
                inMeterPersegi = input * 2589988.11;
                break;
            case "Acre":
                inMeterPersegi = input * 4046.86;
                break;
        }

        // Konversi dari meter persegi ke unit tujuan
        switch (keUnit) {
            case "Kilometer Persegi":
                hasil = inMeterPersegi / 1000000;
                break;
            case "Hektare":
                hasil = inMeterPersegi / 10000;
                break;
            case "Meter Persegi":
                hasil = inMeterPersegi;
                break;
            case "Sentimeter Persegi":
                hasil = inMeterPersegi * 10000;
                break;
            case "Mil Persegi":
                hasil = inMeterPersegi / 2589988.11;
                break;
            case "Acre":
                hasil = inMeterPersegi / 4046.86;
                break;
        }

        // Tampilkan hasil
        String hasilStr = String.format("%.2f %s = %.2f %s", input, dariUnit, hasil, keUnit);
        tvHasil.setText(hasilStr);
    }
}