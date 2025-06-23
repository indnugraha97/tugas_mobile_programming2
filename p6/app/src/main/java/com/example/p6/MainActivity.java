package com.example.p6;

import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.*;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    private VisualizerView visualizerView;
    private MediaPlayer mediaPlayer;
    private Visualizer visualizer;

    private String[] country = {"Indonesia", "Mesir", "Turki"};
    private String[] muadzin = {"Syekh Ahmad", "Mahmoud Khalil", "Fatih Koc"};
    private String[] arab = {
            "اللّهُ أَكْبَرُ",
            "اللّهُ أَكْبَرُ",
            "الله أكبر"
    };
    private String[] latin = {
            "Allahu Akbar", "Allahu Akbar", "Allahu Akbar"
    };
    private String[] logo = {
            "https://upload.wikimedia.org/wikipedia/commons/0/0b/Flag_of_Indonesia.png",
            "https://upload.wikimedia.org/wikipedia/commons/f/fe/Flag_of_Egypt.svg",
            "https://upload.wikimedia.org/wikipedia/commons/b/b4/Flag_of_Turkey.svg"
    };
    private int[] audioRes = {R.raw.adzan_indo, R.raw.adzan_mesir, R.raw.adzan_turki};

    private int selectedIdx = -1;
    private LinearLayout detailSection;
    private ImageView flagImage;
    private TextView countryText, muadzinText, arabicText, latinText;
    private ImageButton playButton;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(this, initializationStatus -> {});
        setContentView(R.layout.activity_main);

        // Detail Section
        detailSection = findViewById(R.id.detail_section);
        flagImage = findViewById(R.id.flagImage);
        countryText = findViewById(R.id.countryText);
        muadzinText = findViewById(R.id.muadzinText);
        arabicText = findViewById(R.id.arabicText);
        latinText = findViewById(R.id.latinText);
        visualizerView = findViewById(R.id.visualizerView);
        playButton = findViewById(R.id.playButton);

        // AdMob Banner
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // List Adzan
        RecyclerView recyclerAdzan = findViewById(R.id.recyclerAdzan);
        recyclerAdzan.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdzan.setAdapter(new AdzanAdapter());

        playButton.setOnClickListener(v -> togglePlayer());
    }

    private void showDetail(int idx) {
        selectedIdx = idx;
        detailSection.setVisibility(View.VISIBLE);
        countryText.setText(country[idx]);
        muadzinText.setText("Muadzin: " + muadzin[idx]);
        arabicText.setText(arab[idx]);
        latinText.setText(latin[idx]);
        Glide.with(this).load(logo[idx]).into(flagImage);
        playButton.setImageResource(android.R.drawable.ic_media_play);
        isPlaying = false;

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (visualizer != null) {
            visualizer.setEnabled(false);
            visualizer.release();
            visualizer = null;
        }
        visualizerView.updateVisualizer(null);
    }

    private void togglePlayer() {
        if (selectedIdx == -1) return;

        if (isPlaying) {
            if (mediaPlayer != null) mediaPlayer.pause();
            playButton.setImageResource(android.R.drawable.ic_media_play);
            isPlaying = false;
            if (visualizer != null) visualizer.setEnabled(false);
        } else {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, audioRes[selectedIdx]);
                // Handler supaya Visualizer baru dibuat setelah MediaPlayer benar-benar siap
                new Handler().postDelayed(() -> {
                    try {
                        // Release visualizer lama kalau ada
                        if (visualizer != null) {
                            visualizer.release();
                            visualizer = null;
                        }
                        int audioSessionId = mediaPlayer.getAudioSessionId();
                        if (audioSessionId != -1) {
                            visualizer = new Visualizer(audioSessionId);
                            visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
                            visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
                                @Override
                                public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                                    runOnUiThread(() -> visualizerView.updateVisualizer(waveform));
                                }
                                @Override
                                public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {}
                            }, Visualizer.getMaxCaptureRate() / 2, true, false);
                            visualizer.setEnabled(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Visualizer gagal inisialisasi, coba di HP asli!", Toast.LENGTH_LONG).show();
                    }
                }, 300);
            } else {
                if (visualizer != null) visualizer.setEnabled(true);
            }

            mediaPlayer.start();
            playButton.setImageResource(android.R.drawable.ic_media_pause);
            isPlaying = true;

            mediaPlayer.setOnCompletionListener(mp -> {
                playButton.setImageResource(android.R.drawable.ic_media_play);
                isPlaying = false;
                if (visualizer != null) visualizer.setEnabled(false);
            });
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (visualizer != null) {
            visualizer.release();
            visualizer = null;
        }
        super.onDestroy();
    }

    // List Adapter inner class
    class AdzanAdapter extends RecyclerView.Adapter<AdzanAdapter.AdzanViewHolder> {

        @NonNull
        @Override
        public AdzanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
            return new AdzanViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AdzanViewHolder holder, int position) {
            holder.title.setText(country[position]);
            holder.subtitle.setText("Muadzin: " + muadzin[position]);
            holder.itemView.setBackgroundColor(selectedIdx == position ? 0xFFD7FFE3 : 0xFFFFFFFF);
            holder.itemView.setOnClickListener(v -> showDetail(position));
        }

        @Override
        public int getItemCount() {
            return country.length;
        }

        class AdzanViewHolder extends RecyclerView.ViewHolder {
            TextView title, subtitle;
            public AdzanViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(android.R.id.text1);
                subtitle = itemView.findViewById(android.R.id.text2);
            }
        }
    }
}
