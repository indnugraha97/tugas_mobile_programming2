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

public class KonversiSuhuActivity extends AppCompatActivity {

    private EditText etInputSuhu;
    private Spinner spinnerDari, spinnerKe;
    private Button btnKonversi, btnKembali;
    private TextView tvHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konversi_suhu);

        // Inisialisasi view
        etInputSuhu = findViewById(R.id.etInputSuhu);
        spinnerDari = findViewById(R.id.spinnerDari);
        spinnerKe = findViewById(R.id.spinnerKe);
        btnKonversi = findViewById(R.id.btnKonversi);
        btnKembali = findViewById(R.id.btnKembali);
        tvHasil = findViewById(R.id.tvHasil);

        // Setup spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.suhu_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDari.setAdapter(adapter);
        spinnerKe.setAdapter(adapter);

        // Button konversi click listener
        btnKonversi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                konversiSuhu();
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

    private void konversiSuhu() {
        String inputStr = etInputSuhu.getText().toString();

        if (inputStr.isEmpty()) {
            Toast.makeText(this, "Masukkan nilai suhu terlebih dahulu", Toast.LENGTH_SHORT).show();
            return;
        }

        double input = Double.parseDouble(inputStr);
        String dariUnit = spinnerDari.getSelectedItem().toString();
        String keUnit = spinnerKe.getSelectedItem().toString();

        double hasil = 0;

        // Konversi ke Celcius dulu
        double inCelcius = 0;
        switch (dariUnit) {
            case "Celcius":
                inCelcius = input;
                break;
            case "Fahrenheit":
                inCelcius = (input - 32) * 5/9;
                break;
            case "Kelvin":
                inCelcius = input - 273.15;
                break;
            case "Reamur":
                inCelcius = input * 5/4;
                break;
        }

        // Konversi dari Celcius ke unit tujuan
        switch (keUnit) {
            case "Celcius":
                hasil = inCelcius;
                break;
            case "Fahrenheit":
                hasil = (inCelcius * 9/5) + 32;
                break;
            case "Kelvin":
                hasil = inCelcius + 273.15;
                break;
            case "Reamur":
                hasil = inCelcius * 4/5;
                break;
        }

        // Tampilkan hasil
        String hasilStr = String.format("%.2f %s = %.2f %s", input, dariUnit, hasil, keUnit);
        tvHasil.setText(hasilStr);
    }
}