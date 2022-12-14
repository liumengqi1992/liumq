package com.deepblue.library.adapter.bean;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * @author liumq
 * @date 2015/5/15
 */
public interface AdapterItem<T> {

    /**
     * @return item布局文件的layoutId
     */
    @LayoutRes
    int getLayoutResId();

    /**
     * 初始化views
     */
    void bindViews(@NonNull final View root);

    /**
     * 设置view的参数
     */
    void setViews();

    /**
     * 根据数据来设置item的内部views
     *
     * @param t    数据list内部的model
     * @param position 当前adapter调用item的位置
     */
    void handleData(T t, int position);

}  