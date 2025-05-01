package com.indranugraha.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.indranugraha.myapplication.DetailCeritaActivity;
import com.indranugraha.myapplication.R;
import com.indranugraha.myapplication.Cerita;

import java.util.List;

public class CeritaAdapter extends RecyclerView.Adapter<CeritaAdapter.ViewHolder> {

    private Context context;
    private List<Cerita> ceritaList;

    public CeritaAdapter(Context context, List<Cerita> ceritaList) {
        this.context = context;
        this.ceritaList = ceritaList;
    }

    @Override
    public CeritaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cerita_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CeritaAdapter.ViewHolder holder, int position) {
        Cerita cerita = ceritaList.get(position);
        holder.judul.setText(cerita.getJudul());
        Glide.with(context).load(cerita.getGambar()).into(holder.gambar);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailCeritaActivity.class);
            intent.putExtra("judul", cerita.getJudul());
            intent.putExtra("isi", cerita.getIsi());
            intent.putExtra("gambar", cerita.getGambar());
            intent.putExtra("audio", cerita.getAudio());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ceritaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView judul;
        ImageView gambar;

        public ViewHolder(View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.tvJudul);
            gambar = itemView.findViewById(R.id.ivGambar);
        }
    }
}

