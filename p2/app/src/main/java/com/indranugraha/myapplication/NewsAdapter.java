package com.indranugraha.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private List<DataNews> newsList;

    public NewsAdapter(Context context, List<DataNews> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        DataNews news = newsList.get(position);
        holder.tvTitle.setText(news.getTitle());

        Glide.with(context)
                .load(news.getImageUrl()) // Pastikan getImageUrl() mengembalikan URL gambar
                .into(holder.imgNews);

        holder.webView.getSettings().setJavaScriptEnabled(true);
        holder.webView.loadUrl(news.getLinkNews());

        // Menambahkan klik listener untuk CardView
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsActivity.class);
            intent.putExtra(Constants.NEWS_TITLE, news.getTitle());
            intent.putExtra(Constants.NEWS_IMAGE, news.getImageUrl());
            intent.putExtra(Constants.NEWS_LINK, news.getLinkNews());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNews; // Untuk gambar
        TextView tvTitle;  // Untuk judul
        CardView cardView; // Untuk CardView

        WebView webView; // Untuk WebView

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            imgNews = itemView.findViewById(R.id.img_news); // Pastikan ID di item_news.xml sesuai
            tvTitle = itemView.findViewById(R.id.tv_title);  // ID untuk judul
            cardView = itemView.findViewById(R.id.card_view); // ID untuk CardView
            webView = itemView.findViewById(R.id.webview_news); // ID untuk Web View
        }
    }
}

