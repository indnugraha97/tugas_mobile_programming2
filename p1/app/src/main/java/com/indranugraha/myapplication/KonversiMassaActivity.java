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

public class KonversiMassaActivity extends AppCompatActivity {

    private EditText etInputMassa;
    private Spinner spinnerDari, spinnerKe;
    private Button btnKonversi, btnKembali;
    private TextView tvHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konversi_massa);

        // Inisialisasi view
        etInputMassa = findViewById(R.id.etInputMassa);
        spinnerDari = findViewById(R.id.spinnerDari);
        spinnerKe = findViewById(R.id.spinnerKe);
        btnKonversi = findViewById(R.id.btnKonversi);
        btnKembali = findViewById(R.id.btnKembali);
        tvHasil = findViewById(R.id.tvHasil);

        // Setup spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.massa_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDari.setAdapter(adapter);
        spinnerKe.setAdapter(adapter);

        // Button konversi click listener
        btnKonversi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                konversiMassa();
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void konversiMassa() {
        String inputStr = etInputMassa.getText().toString();

        if (inputStr.isEmpty()) {
            Toast.makeText(this, "Masukkan nilai massa terlebih dahulu", Toast.LENGTH_SHORT).show();
            return;
        }

        double input = Double.parseDouble(inputStr);
        String dariUnit = spinnerDari.getSelectedItem().toString();
        String keUnit = spinnerKe.getSelectedItem().toString();

        double hasil = 0;

        // Konversi ke gram dulu
        double inGram = 0;
        switch (dariUnit) {
            case "Kilogram":
                inGram = input * 1000;
                break;
            case "Gram":
                inGram = input;
                break;
            case "Miligram":
                inGram = input / 1000;
                break;
            case "Pound":
                inGram = input * 453.592;
                break;
            case "Ons":
                inGram = input * 28.3495;
                break;
        }

        // Konversi dari gram ke unit tujuan
        switch (keUnit) {
            case "Kilogram":
                hasil = inGram / 1000;
                break;
            case "Gram":
                hasil = inGram;
                break;
            case "Miligram":
                hasil = inGram * 1000;
                break;
            case "Pound":
                hasil = inGram / 453.592;
                break;
            case "Ons":
                hasil = inGram / 28.3495;
                break;
        }

        // Tampilkan hasil
        String hasilStr = String.format("%.2f %s = %.2f %s", input, dariUnit, hasil, keUnit);
        tvHasil.setText(hasilStr);
    }
}