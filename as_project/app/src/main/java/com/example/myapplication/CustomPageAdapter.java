package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CustomPageAdapter extends PagerAdapter {
    private static final String TAG = "CustomPageAdapter";

    private Context context;

    public CustomPageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d(TAG, "instantiateItem position = " + position);
        View view = LayoutInflater.from(context).inflate(R.layout.item_viewpage, container, false);
        ImageView imageView = view.findViewById(R.id.item_img_iv);
        TextView timeTv = view.findViewById(R.id.item_time_tv);
        TextView placeTv = view.findViewById(R.id.item_place_tv);
        if (position % 2 == 0) {
            imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.report));
            timeTv.setVisibility(View.VISIBLE);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
            Calendar cal = Calendar.getInstance();
            //cal.add(Calendar.DATE, -1);
            timeTv.setText(simpleDateFormat.format(cal.getTime()) + " 01:27:32");
            placeTv.setVisibility(View.VISIBLE);
        } else {
            imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.report_48hour));
            timeTv.setVisibility(View.GONE);
            placeTv.setVisibility(View.GONE);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return 0;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (View) object == view;
    }
}
