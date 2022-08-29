package com.deepblue.dragswipelv;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;
import java.util.ArrayList;

/**
 * @author tn001
 * @date 2014-8-24
 */
public class SwipeMenuAdapter implements WrapperListAdapter,
		SwipeMenuView.OnSwipeItemClickListener {

	private ArrayList<Boolean> hasMenu;
	private ListAdapter mAdapter;
	private Context mContext;
	private DynamicListView.OnMenuItemClickListener onMenuItemClickListener;

	public SwipeMenuAdapter(Context context, ListAdapter adapter) {
		mAdapter = adapter;
		mContext = context;
		this.hasMenu = new ArrayList<Boolean>();
	}

	public SwipeMenuAdapter(Context context, ListAdapter adapter, ArrayList<Boolean> hasMenu) {
		mAdapter = adapter;
		mContext = context;
		this.hasMenu = hasMenu;
	}

	@Override
	public int getCount() {
		return mAdapter.getCount();
	}

	@Override
	public Object getItem(int position) {
		return mAdapter.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return mAdapter.getItemId(position);
	}

	//Need work here : Viewholder pattern to be implemented
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View contentView = mAdapter.getView(position, convertView, parent);
		SwipeMenu menu = new SwipeMenu(mContext);
		menu.setViewType(mAdapter.getItemViewType(position));
		boolean hasSwipe = true;
		if (null != hasMenu) {
			hasSwipe = hasMenu.get(position);
		}
		if (hasSwipe) {
			createMenu(menu);
		}
		SwipeMenuView menuView = new SwipeMenuView(menu, (DynamicListView) parent);
		menuView.setOnSwipeItemClickListener(this);
		DynamicListView listView = (DynamicListView) parent;
		SwipeMenuLayout layout = new SwipeMenuLayout(contentView, menuView, listView.getCloseInterpolator(), listView.getOpenInterpolator());
		layout.setPosition(position);		
		layout.closeMenu();

		return layout;
	}

	public void createMenu(SwipeMenu menu) {
		// Test Code
		SwipeMenuItem item = new SwipeMenuItem(mContext);
		item.setTitle("Item 1");
		item.setBackground(new ColorDrawable(Color.GRAY));
		item.setWidth(300);
		menu.addMenuItem(item);

		item = new SwipeMenuItem(mContext);
		item.setTitle("Item 2");
		item.setBackground(new ColorDrawable(Color.RED));
		item.setWidth(300);
		menu.addMenuItem(item);
	}

	@Override
	public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
		if (onMenuItemClickListener != null) {
			onMenuItemClickListener.onMenuItemClick(view.getPosition(), menu,
					index);
		}
	}

	public void setOnMenuItemClickListener(
			DynamicListView.OnMenuItemClickListener onMenuItemClickListener) {
		this.onMenuItemClickListener = onMenuItemClickListener;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		mAdapter.registerDataSetObserver(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		mAdapter.unregisterDataSetObserver(observer);
	}

	@Override
	public boolean areAllItemsEnabled() {
		return mAdapter.areAllItemsEnabled();
	}

	@Override
	public boolean isEnabled(int position) {
		return mAdapter.isEnabled(position);
	}

	@Override
	public boolean hasStableIds() {
		return mAdapter.hasStableIds();
	}

	@Override
	public int getItemViewType(int position) {
		return mAdapter.getItemViewType(position);
	}

	@Override
	public int getViewTypeCount() {
		return mAdapter.getViewTypeCount();
	}

	@Override
	public boolean isEmpty() {
		return mAdapter.isEmpty();
	}

	@Override
	public ListAdapter getWrappedAdapter() {
		return mAdapter;
	}

}
