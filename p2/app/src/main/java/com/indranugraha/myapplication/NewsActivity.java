package com.indranugraha.myapplication;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class NewsActivity extends AppCompatActivity {

    private TextView tvTitle;
    private ImageView imgNews;
    private WebView webViewNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Ambil data dari Intent
        String title = getIntent().getStringExtra(Constants.NEWS_TITLE);
        String linkNews = getIntent().getStringExtra(Constants.NEWS_LINK);
        String imageUrl = getIntent().getStringExtra(Constants.NEWS_IMAGE);

        // Inisialisasi view
        tvTitle = findViewById(R.id.tv_title);
        imgNews = findViewById(R.id.img_news);
        webViewNews = findViewById(R.id.webview_news);

        // Set Title dan Image
        tvTitle.setText(title);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this).load(imageUrl).into(imgNews);
        }

        // Set WebView
        WebSettings webSettings = webViewNews.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if (linkNews != null) {
            webViewNews.loadUrl(linkNews);
        }
    }
}
