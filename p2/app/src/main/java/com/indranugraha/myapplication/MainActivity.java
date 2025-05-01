package com.indranugraha.myapplication;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<DataNews> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageThumbnail = findViewById(R.id.image_thumbnail);
        Glide.with(this).load("https://marketplace.canva.com/EAFHzeyP6Ps/2/0/1600w/canva-red-bold-news-youtube-thumbnail-yTqx8fOM10c.jpg").into(imageThumbnail);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadNewsData();

        adapter = new NewsAdapter(this, newsList);
        recyclerView.setAdapter(adapter);
    }

    private void loadNewsData() {
        try {
            InputStream is = getAssets().open("news.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(Constants.NEWS_DATA);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String title = obj.getString(Constants.NEWS_TITLE);
                String content = obj.getString(Constants.NEWS_LINK);
                String imageUrl = obj.getString(Constants.NEWS_IMAGE);
                String linkNews = obj.getString(Constants.NEWS_LINK);

                newsList.add(new DataNews(title, content, imageUrl, linkNews));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}