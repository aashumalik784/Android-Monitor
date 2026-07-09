package com.androidmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.graphics.*;

public class MainActivity extends Activity {
    private TextView tvInfo;
    private Button btnBenchmark;
    private ProgressBar progressBar;

    static { System.loadLibrary("native-lib"); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.parseColor("#1E1E1E"));
        layout.setPadding(50, 50, 50, 50);

        TextView title = new TextView(this);
        title.setText("ANDROID MONITOR");
        title.setTextColor(Color.WHITE);
        title.setTextSize(28);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, 40);

        tvInfo = new TextView(this);
        tvInfo.setText("Loading System Data...");
        tvInfo.setTextColor(Color.parseColor("#00FF00"));
        tvInfo.setTextSize(18);
        tvInfo.setTypeface(Typeface.MONOSPACE);

        btnBenchmark = new Button(this);
        btnBenchmark.setText("RUN BENCHMARK ⚡");
        btnBenchmark.setBackgroundColor(Color.parseColor("#007ACC"));
        btnBenchmark.setTextColor(Color.WHITE);
        btnBenchmark.setPadding(0, 30, 0, 30);

        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setVisibility(View.GONE);

        layout.addView(title);
        layout.addView(tvInfo);
        layout.addView(btnBenchmark);
        layout.addView(progressBar);
        setContentView(layout);

        tvInfo.setText(getSystemData());

        btnBenchmark.setOnClickListener(v -> {
            btnBenchmark.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            new Thread(() -> {
                final long timeMs = runSpeedTest();
                runOnUiThread(() -> {
                    tvInfo.setText(tvInfo.getText() + "\n\nTime: " + timeMs + " ms");
                    btnBenchmark.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                });
            }).start();
        });
    }
    public native String getSystemData();
    public native long runSpeedTest();
}
