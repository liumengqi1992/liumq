package com.deepblue.libraries;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.deepblue.library.adapter.bean.AdapterItem;
import com.deepblue.library.listview.DragSortAdapter;
import com.deepblue.library.listview.DragSortListView;

import java.util.ArrayList;
import java.util.List;

public class DragSortListActivity extends AppCompatActivity {

    private DragSortAdapter dragSortAdapter;
    private ArrayList<DemoModel> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragsortlist);

        for (int i = 0;i < 20;i ++) {
            DemoModel dm = new DemoModel();
            dm.content = "content: " + i;
            list.add(dm);
        }
        dragSortAdapter = singleType(list);

        final DragSortListView dragSortListView = findViewById(R.id.dragSortListView);
        dragSortListView.setAdapter(dragSortAdapter);
    }

    private DragSortAdapter<DemoModel> singleType(List<DemoModel> data) {
        return new DragSortAdapter<DemoModel>(data, 1) {

            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                // 如果就一种，那么直接return一种类型的item即可。
                return new TextItem();
            }
        };
    }
}
