package com.deepblue.appotalib;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import java.awt.font.TextAttribute;
import java.util.Arrays;

import static com.deepblue.appotalib.ConstantUtil.SEPARATOR;
import static com.deepblue.appotalib.ConstantUtil.TYPE_CLEAN;
import static com.deepblue.appotalib.ConstantUtil.TYPE_CONTAIN;
import static com.deepblue.appotalib.ConstantUtil.VALUE;


public class AppStatusContentProvider extends ContentProvider {
    private static final String TAG = "AppStatusContentProvider";
    public AppStatusContentProvider() {
    }

    @SuppressLint("LongLogTag")
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        Log.v(TAG, "delete: uri=" + uri +
                "  selection=[" + selection + "]  args=" + Arrays.toString(selectionArgs) +
                " CPID=" + Binder.getCallingPid());
        String[] path= uri.getPath().split(SEPARATOR);
        String type=path[1];
        if (type.equals(TYPE_CLEAN)){
            SPHelperImpl.clear(getContext());
            return 0;
        }
        String key=path[2];
        if (SPHelperImpl.contains(getContext(),key)){
            SPHelperImpl.remove(getContext(),key);
        }
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        String[] path= uri.getPath().split(SEPARATOR);
        String type=path[1];
        String key=path[2];
        if (type.equals(TYPE_CONTAIN)){
            if(!SPHelperImpl.contains(getContext(),key)) {
                return null;
            }
            return SPHelperImpl.contains(getContext(),key)+"";
        }
        return  SPHelperImpl.get(getContext(),key,type);
    }

    @SuppressLint("LongLogTag")
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.v(TAG, "insert: uri=" + uri + "  values=[" + values + "]" +
                " CPID=" + Binder.getCallingPid());
        String[] path= uri.getPath().split(SEPARATOR);
        String type=path[1];
        String key=path[2];
        Object obj= (Object) values.get(VALUE);
        if (obj!=null) {
            SPHelperImpl.save(getContext(), key, obj);
        }
        return null;
    }

    @SuppressLint("LongLogTag")
    @Override
    public boolean onCreate() {
        Log.d(TAG,"onCreate");
        return true;
    }

    @SuppressLint("LongLogTag")
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.v(TAG, "query: uri=" + uri + "  projection=" + Arrays.toString(projection) +
                "  selection=[" + selection + "]  args=" + Arrays.toString(selectionArgs) +
                "  order=[" + sortOrder + "] CPID=" + Binder.getCallingPid());
        return null;
    }

    @SuppressLint("LongLogTag")
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Log.v(TAG, "update: uri=" + uri +
                "  selection=[" + selection + "]  args=" + Arrays.toString(selectionArgs) +
                "  values=[" + values + "] CPID=" + Binder.getCallingPid());
        insert(uri,values);
        return 0;
    }
}
