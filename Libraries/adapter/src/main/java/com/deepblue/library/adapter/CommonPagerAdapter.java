package com.deepblue.library.adapter;

import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.deepblue.library.adapter.bean.AdapterItem;
import com.deepblue.library.adapter.bean.IAdapter;
import com.deepblue.library.adapter.utils.DataBindingJudgement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liumq
 * @date 2015/11/29
 */
public abstract class CommonPagerAdapter<T> extends BasePagerAdapter<View> implements IAdapter<T> {

    private List<T> mDataList;

    private LayoutInflater mInflater;

    private boolean mIsLazy = false;

    private int currentPos;
    
    public CommonPagerAdapter(@Nullable List<T> data) {
        this(data, false);
    }

    public CommonPagerAdapter(@Nullable List<T> data, boolean isLazy) {
        if (data == null) {
            data = new ArrayList<>();
        }
        
        if (DataBindingJudgement.SUPPORT_DATABINDING && data instanceof ObservableList) {
            ((ObservableList<T>) data).addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
                @Override
                public void onChanged(ObservableList<T> sender) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                    notifyDataSetChanged();
                }
            });
        }
        mDataList = data;
        mIsLazy = isLazy;
    }

    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @NonNull
    @Override
    protected View getViewFromItem(View item, int pos) {
        return item;
    }

    @NonNull
    @Override
    public View instantiateItem(@NonNull ViewGroup container, int position) {
        View view = super.instantiateItem(container, position);
        if (!mIsLazy) {
            initItem(position, view);
        }
        return view;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (mIsLazy && object != currentItem) {
            initItem(position, ((View) object));
        }
        super.setPrimaryItem(container, position, object);
    }

    private void initItem(int position, View view) {
        final AdapterItem item = (AdapterItem) view.getTag(R.id.tag_item);
        item.handleData(getConvertedData(mDataList.get(position), getItemType(position)), position);
    }

    @Override
    protected View createItem(ViewGroup viewPager, int position) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(viewPager.getContext());
        }
        AdapterItem item = createItem(getItemType(position));
        View view = mInflater.inflate(item.getLayoutResId(), null);
        view.setTag(R.id.tag_item, item);
        item.bindViews(view);
        item.setViews();
        return view;
    }

    public void setIsLazy(boolean isLazy) {
        mIsLazy = isLazy;
    }

    @NonNull
    @Override
    public Object getConvertedData(T data, Object type) {
        return data;
    }

    /**
     * instead by {@link #getItemType(Object)}
     */
    @Deprecated
    protected Object getItemType(int position) {
        currentPos = position;
        if (position < mDataList.size()) {
            return getItemType(mDataList.get(position));
        } else {
            return null;
        }
    }

    /**
     * ??????????????????string,int,bool????????????????????????type
     */
    @Override
    public Object getItemType(T t) {
        return -1; // default
    }

    @Override
    public void setData(@NonNull List<T> data) {
        mDataList = data;
    }

    @Override
    public List<T> getData() {
        return mDataList;
    }

    @Override
    public int getCurrentPosition() {
        return currentPos;
    }
}
