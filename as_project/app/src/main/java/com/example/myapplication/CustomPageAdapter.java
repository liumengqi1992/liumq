package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomPageAdapter extends PagerAdapter {
    private List<String> dataList = new ArrayList<>();

    private Context context;

    public CustomPageAdapter(Context context) {
        this.context = context;
        dataList.add("page 1");
        dataList.add("page 2");
        dataList.add("page 3");
        dataList.add("page 4");
        dataList.add("page 5");
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = createItem(container);
        container.addView(view);
        return view;
    }

    private View createItem(ViewGroup container) {
        return LayoutInflater.from(context).inflate(R.layout.item_viewpage, container, false);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (View) object == view;
    }
}
