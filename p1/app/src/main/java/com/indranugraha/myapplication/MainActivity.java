package com.indranugraha.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    CardView cdTemp;
    CardView cdMassa;
    CardView cdLuas;
    CardView cdJarak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cdTemp = findViewById(R.id.cardSuhu);
        cdTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, KonversiSuhuActivity.class));
            }
        });

        cdMassa = findViewById(R.id.cardMassa);
        cdMassa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, KonversiMassaActivity.class));
            }
        });

        cdLuas = findViewById(R.id.cardLuas);
        cdLuas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, KonversiLuasActivity.class));
            }
        });

        cdJarak = findViewById(R.id.cardJarak);
        cdJarak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, KonversiJarakActivity.class));
            }
        });
    }
}