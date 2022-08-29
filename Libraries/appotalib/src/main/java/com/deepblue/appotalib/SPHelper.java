package com.deepblue.appotalib;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import static com.deepblue.appotalib.ConstantUtil.CONTENT_URI;
import static com.deepblue.appotalib.ConstantUtil.SEPARATOR;
import static com.deepblue.appotalib.ConstantUtil.TYPE_BOOLEAN;
import static com.deepblue.appotalib.ConstantUtil.TYPE_STRING;
import static com.deepblue.appotalib.ConstantUtil.VALUE;

class SPHelper {
    public static Context mContext;

    public static boolean checkContext() {
        if (mContext == null) {
            return false;
        }
        return true;
    }

    public  synchronized static void init(Context context) {
        if(mContext != null) {
            return;
        }
        mContext = context.getApplicationContext();
    }

    public synchronized static void save(String name, Boolean t) {
        if(!checkContext()) {
            return;
        }
        try {
            ContentResolver cr = mContext.getContentResolver();
            Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_BOOLEAN + SEPARATOR + name);
            ContentValues cv = new ContentValues();
            cv.put(VALUE, t);
            cr.update(uri, cv, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean getBoolean(String name, boolean defaultValue) {
        if(!checkContext()) {
            return defaultValue;
        }
        String rtn = null;
        try {
            ContentResolver cr = mContext.getContentResolver();
            Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_BOOLEAN + SEPARATOR + name);
            rtn = cr.getType(uri);
        } catch (Exception e) {
            return defaultValue;
        }
        if (TextUtils.isEmpty(rtn)) {
            return defaultValue;
        }
        return Boolean.parseBoolean(rtn);
    }

    public synchronized static void saveString(String name, String t) {
        if(!checkContext()) {
            return;
        }
        try {
            ContentResolver cr = mContext.getContentResolver();
            Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_STRING + SEPARATOR + name);
            ContentValues cv = new ContentValues();
            cv.put(VALUE, t);
            cr.update(uri, cv, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getString(String name, String defaultValue) {
        if(!checkContext()) {
            return defaultValue;
        }
        String rtn = null;
        try {
            ContentResolver cr = mContext.getContentResolver();
            Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_STRING + SEPARATOR + name);
            rtn = cr.getType(uri);
        } catch (Exception e) {
            return defaultValue;
        }

        if (TextUtils.isEmpty(rtn)) {
            return defaultValue;
        }
        return rtn;
    }
}