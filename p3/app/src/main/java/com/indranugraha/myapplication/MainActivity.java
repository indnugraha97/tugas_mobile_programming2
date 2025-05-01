package com.indranugraha.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private Button btnStart, btnShare, btnReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnShare = findViewById(R.id.btnShare);
        btnReview = findViewById(R.id.btnReview);

        btnStart.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ListCeritaActivity.class));
        });

        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String body = "Download aplikasinya di: https://play.google.com/store/apps/details?id=" + getPackageName();
            shareIntent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(shareIntent, "Bagikan lewat:"));
        });

        btnReview.setOnClickListener(v -> {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(goToMarket);
        });

        String imageUrl = "https://schoolofparenting.id/wp-content/uploads/2018/07/feature-image-09.jpg";  // Ganti dengan URL gambar yang diinginkan
        Glide.with(this)
                .load(imageUrl)
                .into((ImageView) findViewById(R.id.ivWelcomeImage));  // Memuat gambar ke ImageView
    }
}
