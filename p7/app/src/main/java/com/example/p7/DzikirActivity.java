package com.example.p7;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.p7.R;

import java.util.*;

public class DzikirActivity extends AppCompatActivity {

    private List<Map<String, Object>> dzikirList;
    private int page = 0;
    private List<Boolean> showArab, showLatin;
    private List<Float> fontSizes;
    private List<Integer> counters;

    // UI
    private TextView txtJudul, txtArab, txtLatin, txtArti, txtCounter;
    private Switch switchArab, switchLatin;
    private ImageButton btnPrev, btnNext, btnCopy, btnFontPlus, btnFontMinus, btnAudio;
    private Button btnIncCounter, btnResetCounter;
    private LinearLayout counterLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dzikir);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Dzikir Pagi");
        setSupportActionBar(toolbar);

        // DATA DZIKIR
        dzikirList = new ArrayList<>();
        dzikirList.add(new HashMap<String, Object>() {{
            put("judul", "Ayat Kursi");
            put("arab", "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ، ...وَهُوَ الْعَلِيُّ الْعَظِيمُ");
            put("latin", "Allahu laa ilaaha illaa huwal hayyul qayyuum...");
            put("arti", "Allah, tidak ada Tuhan melainkan Dia Yang Maha Hidup, terus-menerus mengurus (makhluk-Nya)...");
            put("audio", "");
            put("jumlah", 1);
        }});
        dzikirList.add(new HashMap<String, Object>() {{
            put("judul", "Sayyidul Istighfar");
            put("arab", "اللَّهُمَّ أَنْتَ رَبِّي لا إِلَهَ إِلا أَنْتَ...");
            put("latin", "Allahumma anta rabbii laa ilaaha illaa anta...");
            put("arti", "Ya Allah, Engkau adalah Rabbku, tidak ada Tuhan selain Engkau...");
            put("audio", "");
            put("jumlah", 1);
        }});
        dzikirList.add(new HashMap<String, Object>() {{
            put("judul", "Dzikir Tasbih Pagi");
            put("arab", "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ");
            put("latin", "Subhanallahi wabihamdih.");
            put("arti", "Maha suci Allah dan segala puji bagi-Nya.");
            put("audio", "");
            put("jumlah", 100);
        }});

        int N = dzikirList.size();
        showArab = new ArrayList<>(Collections.nCopies(N, true));
        showLatin = new ArrayList<>(Collections.nCopies(N, true));
        fontSizes = new ArrayList<>(Collections.nCopies(N, 26.0f));
        counters = new ArrayList<>();
        for (int i = 0; i < N; i++)
            counters.add((int) dzikirList.get(i).get("jumlah") > 1 ? 0 : 1);

        // FIND UI
        txtJudul = findViewById(R.id.txtJudul);
        txtArab = findViewById(R.id.txtArab);
        txtLatin = findViewById(R.id.txtLatin);
        txtArti = findViewById(R.id.txtArti);
        txtCounter = findViewById(R.id.txtCounter);
        switchArab = findViewById(R.id.switchArab);
        switchLatin = findViewById(R.id.switchLatin);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnCopy = findViewById(R.id.btnCopy);
        btnFontPlus = findViewById(R.id.btnFontPlus);
        btnFontMinus = findViewById(R.id.btnFontMinus);
        btnAudio = findViewById(R.id.btnAudio);
        btnIncCounter = findViewById(R.id.btnIncCounter);
        btnResetCounter = findViewById(R.id.btnResetCounter);
        counterLayout = findViewById(R.id.counterLayout);

        updateUI();

        // FUNGSI BUTTON
        btnPrev.setOnClickListener(v -> {
            if (page > 0) {
                page--;
                updateUI();
            }
        });
        btnNext.setOnClickListener(v -> {
            if (page < dzikirList.size() - 1) {
                page++;
                updateUI();
            }
        });
        btnCopy.setOnClickListener(v -> {
            String all = dzikirList.get(page).get("arab") + "\n" + dzikirList.get(page).get("latin") + "\n" + dzikirList.get(page).get("arti");
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setPrimaryClip(ClipData.newPlainText("dzikir", all));
            Toast.makeText(this, "Teks berhasil disalin", Toast.LENGTH_SHORT).show();
        });
        btnFontPlus.setOnClickListener(v -> {
            fontSizes.set(page, Math.min(fontSizes.get(page) + 2, 50));
            updateUI();
        });
        btnFontMinus.setOnClickListener(v -> {
            fontSizes.set(page, Math.max(fontSizes.get(page) - 2, 16));
            updateUI();
        });
        switchArab.setOnCheckedChangeListener((b, val) -> {
            showArab.set(page, val);
            updateUI();
        });
        switchLatin.setOnCheckedChangeListener((b, val) -> {
            showLatin.set(page, val);
            updateUI();
        });
        btnAudio.setOnClickListener(v -> Toast.makeText(this, "Audio belum tersedia.", Toast.LENGTH_SHORT).show());

        btnIncCounter.setOnClickListener(v -> {
            int jumlah = (int) dzikirList.get(page).get("jumlah");
            if (counters.get(page) < jumlah) {
                counters.set(page, counters.get(page) + 1);
                updateUI();
            }
        });
        btnResetCounter.setOnClickListener(v -> {
            counters.set(page, 0);
            updateUI();
        });
    }

    private void updateUI() {
        Map<String, Object> dzikir = dzikirList.get(page);
        txtJudul.setText((String) dzikir.get("judul"));

        txtArab.setText((String) dzikir.get("arab"));
        txtLatin.setText((String) dzikir.get("latin"));
        txtArti.setText((String) dzikir.get("arti"));

        txtArab.setVisibility(showArab.get(page) ? View.VISIBLE : View.GONE);
        txtLatin.setVisibility(showLatin.get(page) ? View.VISIBLE : View.GONE);

        // Atur font size per page
        txtArab.setTextSize(fontSizes.get(page));
        txtLatin.setTextSize(fontSizes.get(page) - 2);

        // Counter
        int jumlah = (int) dzikir.get("jumlah");
        if (jumlah > 1) {
            counterLayout.setVisibility(View.VISIBLE);
            txtCounter.setText(counters.get(page) + " / " + jumlah);
        } else {
            counterLayout.setVisibility(View.GONE);
        }
    }
}
