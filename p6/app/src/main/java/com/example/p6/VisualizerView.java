package com.example.p6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class VisualizerView extends View {
    private byte[] waveform;
    private Paint paint = new Paint();

    public VisualizerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setStrokeWidth(5f);
        paint.setAntiAlias(true);
        paint.setColor(0xFFFFEB3B); // kuning
    }

    public void updateVisualizer(byte[] waveform) {
        this.waveform = waveform;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (waveform == null) return;

        float width = getWidth();
        float height = getHeight();
        float centerY = height / 2f;
        int n = waveform.length;

        for (int i = 0; i < n - 1; i++) {
            float x1 = width * i / (n - 1);
            float x2 = width * (i + 1) / (n - 1);

            float y1 = centerY + ((byte) (waveform[i] + 128)) * (height / 256f);
            float y2 = centerY + ((byte) (waveform[i + 1] + 128)) * (height / 256f);

            canvas.drawLine(x1, y1, x2, y2, paint);
        }
    }
}
