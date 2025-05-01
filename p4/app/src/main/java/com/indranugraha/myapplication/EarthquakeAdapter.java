package com.indranugraha.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.ViewHolder> {
    private List<Earthquake> earthquakeList;
    private Context context;

    public EarthquakeAdapter(List<Earthquake> earthquakeList, Context context) {
        this.earthquakeList = earthquakeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_earthquake, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Earthquake earthquake = earthquakeList.get(position);

        if (position % 2 == 0) {
            holder.cardViewBackground.setCardBackgroundColor(Color.parseColor("#F5F5F5")); // abu muda
        } else {
            holder.cardViewBackground.setCardBackgroundColor(Color.WHITE);
        }


        holder.tvTanggal.setText("Tanggal: " + earthquake.getTanggal());
        holder.tvJam.setText(earthquake.getJam());
        holder.tvMagnitude.setText(earthquake.getMagnitude() + " SR");
        holder.tvKedalaman.setText(earthquake.getKedalaman());
        holder.tvWilayah.setText(earthquake.getWilayah());

        if (earthquake.getDirasakan() != null) {
            holder.tvDirasakan.setVisibility(View.VISIBLE);
            holder.tvDirasakan.setText(earthquake.getDirasakan());
        } else {
            holder.tvDirasakan.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MapsActivity.class);
            intent.putExtra("latitude", earthquake.getLatitude());
            intent.putExtra("longitude", earthquake.getLongitude());
            intent.putExtra("title", earthquake.getMagnitude() + " SR - " + earthquake.getWilayah());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return earthquakeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTanggal, tvJam, tvMagnitude, tvKedalaman, tvWilayah, tvDirasakan;
        CardView cardViewBackground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvJam = itemView.findViewById(R.id.tvJam);
            tvMagnitude = itemView.findViewById(R.id.tvMagnitude);
            tvKedalaman = itemView.findViewById(R.id.tvKedalaman);
            tvWilayah = itemView.findViewById(R.id.tvWilayah);
            tvDirasakan = itemView.findViewById(R.id.tvDirasakan);
            cardViewBackground = itemView.findViewById(R.id.cardViewBackground);
        }
    }
}