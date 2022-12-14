package com.deepblue.library.adapter;

import android.content.Context;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.deepblue.library.adapter.bean.AdapterItem;
import com.deepblue.library.adapter.bean.IAdapter;
import com.deepblue.library.adapter.utils.DataBindingJudgement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author liumq
 * @date 2015/5/17
 */
public abstract class CommonRcvAdapter<T> extends RecyclerView.Adapter<CommonRcvAdapter.RcvAdapterItem> implements IAdapter<T> {

    private List<T> mDataList;

    private Object mType;

    private ItemTypeUtil mUtil;

    private int currentPos;

    public CommonRcvAdapter(@Nullable List<T> data) {
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
                    notifyItemRangeChanged(positionStart, itemCount);
                }

                @Override
                public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                    notifyItemRangeInserted(positionStart, itemCount);
                    notifyChange(sender, positionStart);
                }

                @Override
                public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                    notifyItemRangeRemoved(positionStart, itemCount);
                    notifyChange(sender, positionStart);
                }

                @Override
                public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
                    notifyChange(sender, Math.min(fromPosition, toPosition));
                }

                private void notifyChange(ObservableList<T> sender, int start) {
                    onItemRangeChanged(sender, start, getItemCount() - start);
                }

            });
        }
        mDataList = data;
        mUtil = new ItemTypeUtil();
    }


    /**
     * ??????RecyclerView???pool?????????TypePool
     * @param typePool
     */
    public void setTypePool(HashMap<Object, Integer> typePool) {
        mUtil.setTypePool(typePool);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
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
    public long getItemId(int position) {
        return position;
    }

    /**
     * instead by{@link #getItemType(Object)}
     * <p>
     * ??????????????????obj????????????type
     * ???????????????{@link ItemTypeUtil}????????????int?????????type
     */
    @Deprecated
    @Override
    public int getItemViewType(int position) {
        this.currentPos = position;
        mType = getItemType(mDataList.get(position));
        return mUtil.getIntType(mType);
    }

    @Override
    public Object getItemType(T t) {
        return -1; // default
    }

    @NonNull
    @Override
    public RcvAdapterItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RcvAdapterItem(parent.getContext(), parent, createItem(mType));
    }

    @Override
    public void onBindViewHolder(@NonNull RcvAdapterItem holder, int position) {
        debug(holder);
        holder.item.handleData(getConvertedData(mDataList.get(position), mType), position);
    }

    @NonNull
    @Override
    public Object getConvertedData(T data, Object type) {
        return data;
    }

    @Override
    public int getCurrentPosition() {
        return currentPos;
    }

    ///////////////////////////////////////////////////////////////////////////
    // ???????????????viewHold
    ///////////////////////////////////////////////////////////////////////////

    public static class RcvAdapterItem extends RecyclerView.ViewHolder {

        protected AdapterItem item;

        boolean isNew = true; // debug????????????

        RcvAdapterItem(Context context, ViewGroup parent, AdapterItem item) {
            super(LayoutInflater.from(context).inflate(item.getLayoutResId(), parent, false));
            this.item = item;
            this.item.bindViews(itemView);
            this.item.setViews();
        }
        
    }

    ///////////////////////////////////////////////////////////////////////////
    // For debug
    ///////////////////////////////////////////////////////////////////////////

    private void debug(RcvAdapterItem holder) {
        boolean debug = false;
        if (debug) {
            holder.itemView.setBackgroundColor(holder.isNew ? 0xffff0000 : 0xff00ff00);
            holder.isNew = false;
        }
    }

}
