package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends Activity {

    private TextView timeTv;

    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        public void run() {
            String currentDateTimeString = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.ROOT).format(new Date());
            timeTv.setText(currentDateTimeString);
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().hide();
        TextView tipTv = findViewById(R.id.tip_tv);
        tipTv.requestFocus();
        px2Dp();
        timeTv = findViewById(R.id.time_tv);
        handler.post(runnable);
    }

    private void px2Dp() {
        final float scale = getResources().getDisplayMetrics().density;
        float width = 1152f / scale + 0.5f;
        float height = 3268f / scale + 0.5f;
        Log.d("LIU", "width = " + width + ", height = " + height);
    }
}