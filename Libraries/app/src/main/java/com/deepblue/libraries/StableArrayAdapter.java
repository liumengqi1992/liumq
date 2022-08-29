package com.deepblue.libraries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class StableArrayAdapter extends ArrayAdapter<String> {

    final int INVALID_ID = -1;
    HashMap<String, Integer> mIdMap = new HashMap<>();
    private ArrayList<String> items;
    private LayoutInflater inflater;

    public StableArrayAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
        super(context, textViewResourceId, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = objects;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    //Need work here : Viewholder pattern to be implemented
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.text_view, null);
        TextView text = (TextView) convertView.findViewById(R.id.text1);
        text.setText(getItem(position));
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size() || position >= items.size()) {
            return INVALID_ID;
        }
        String item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void deleteItem(int position) {
        if (position >= 0 && position < items.size()) {
            items.remove(position);
            notifyDataSetChanged();
        }
    }
}
