package com.indranugraha.myapplication;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Locale;

public class DetailCeritaActivity extends AppCompatActivity {

    private TextView tvJudul, tvIsi;
    private ImageView ivGambar;
    private Button btnPlay;
    private  Button btnBack;
    private TextToSpeech tts;
    private MediaPlayer mediaPlayer;
    private String audio;
    private String isiCerita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cerita);

        tvJudul = findViewById(R.id.tvJudul);
        tvIsi = findViewById(R.id.tvIsi);
        ivGambar = findViewById(R.id.ivGambar);
        btnPlay = findViewById(R.id.btnPlay);
        btnBack = findViewById(R.id.btnBack);

        String judul = getIntent().getStringExtra("judul");
        isiCerita = getIntent().getStringExtra("isi");
        String gambar = getIntent().getStringExtra("gambar");
        audio = getIntent().getStringExtra("audio");

        tvJudul.setText(judul);
        tvIsi.setText(isiCerita);
        Glide.with(this).load(gambar).into(ivGambar);

        tts = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(new Locale("id", "ID"));
            }
        });

        btnPlay.setOnClickListener(v -> {
            if (audio.equals("GT")) {
                tts.speak(isiCerita, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(audio); // langsung dari URL
                        mediaPlayer.prepare(); // prepare async kalau mau lebih advanced
                        mediaPlayer.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            }
        });

        btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
