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

public class KonversiJarakActivity extends AppCompatActivity {

    private EditText etInputJarak;
    private Spinner spinnerDari, spinnerKe;
    private Button btnKonversi, btnKembali;
    private TextView tvHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konversi_jarak);

        // Inisialisasi view
        etInputJarak = findViewById(R.id.etInputJarak);
        spinnerDari = findViewById(R.id.spinnerDari);
        spinnerKe = findViewById(R.id.spinnerKe);
        btnKonversi = findViewById(R.id.btnKonversi);
        btnKembali = findViewById(R.id.btnKembali);
        tvHasil = findViewById(R.id.tvHasil);

        // Setup spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.jarak_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDari.setAdapter(adapter);
        spinnerKe.setAdapter(adapter);

        // Button konversi click listener
        btnKonversi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                konversiJarak();
            }
        });

        // Button kembali click listener
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void konversiJarak() {
        String inputStr = etInputJarak.getText().toString();

        if (inputStr.isEmpty()) {
            Toast.makeText(this, "Masukkan nilai jarak terlebih dahulu", Toast.LENGTH_SHORT).show();
            return;
        }

        double input = Double.parseDouble(inputStr);
        String dariUnit = spinnerDari.getSelectedItem().toString();
        String keUnit = spinnerKe.getSelectedItem().toString();

        double hasil = 0;

        // Konversi ke meter dulu
        double inMeter = 0;
        switch (dariUnit) {
            case "Kilometer":
                inMeter = input * 1000;
                break;
            case "Meter":
                inMeter = input;
                break;
            case "Sentimeter":
                inMeter = input / 100;
                break;
            case "Milimeter":
                inMeter = input / 1000;
                break;
            case "Mil":
                inMeter = input * 1609.34;
                break;
            case "Yard":
                inMeter = input * 0.9144;
                break;
            case "Kaki":
                inMeter = input * 0.3048;
                break;
            case "Inci":
                inMeter = input * 0.0254;
                break;
        }

        // Konversi dari meter ke unit tujuan
        switch (keUnit) {
            case "Kilometer":
                hasil = inMeter / 1000;
                break;
            case "Meter":
                hasil = inMeter;
                break;
            case "Sentimeter":
                hasil = inMeter * 100;
                break;
            case "Milimeter":
                hasil = inMeter * 1000;
                break;
            case "Mil":
                hasil = inMeter / 1609.34;
                break;
            case "Yard":
                hasil = inMeter / 0.9144;
                break;
            case "Kaki":
                hasil = inMeter / 0.3048;
                break;
            case "Inci":
                hasil = inMeter / 0.0254;
                break;
        }

        // Tampilkan hasil
        String hasilStr = String.format("%.2f %s = %.2f %s", input, dariUnit, hasil, keUnit);
        tvHasil.setText(hasilStr);
    }
}