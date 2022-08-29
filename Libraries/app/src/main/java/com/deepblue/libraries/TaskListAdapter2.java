package com.deepblue.libraries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.deepblue.library.planbmsg.bean.ListItemTask;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskListAdapter2 extends ArrayAdapter<ListItemTask> {

    final int INVALID_ID = -1;
    private HashMap<String, Integer> mIdMap = new HashMap<>();
    private ArrayList<ListItemTask> items;
    private LayoutInflater inflater;

    public TaskListAdapter2(Context context, ArrayList<ListItemTask> list) {
        super(context, R.layout.gro_item_task, list);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = list;
        for (int i = 0; i < list.size(); ++i) {
            ListItemTask item = list.get(i);
            mIdMap.put(item.getName() + item.getId(), i);
        }
    }

    @Override
    public ListItemTask getItem(int position) {
        return items.get(position);
    }

    //Need work here : Viewholder pattern to be implemented
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.gro_item_task, null);
        TextView text = convertView.findViewById(R.id.text);
        text.setText(getItem(position).getName());
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || /*position >= mIdMap.size() || */position >= items.size()) {
            return INVALID_ID;
        }
//        String item = getItem(position).getName();
//        return mIdMap.get(item);
        ListItemTask item = getItem(position);
        String key = item.getName() + item.getId();
        return mIdMap.get(key);
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
