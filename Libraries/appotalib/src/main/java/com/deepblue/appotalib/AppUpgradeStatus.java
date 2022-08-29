package com.deepblue.appotalib;

import android.app.Application;
import android.content.Context;

/**
 * 提供给被升级app调用
 * 设置app是否可以升级的状态
 */
public class AppUpgradeStatus {

    /**
     * 初始化context
     * @param context
     * 使用的是ApplicationContext，所以该接口只需要在有context的对象中调用一次就可以
     * 必须在setAppUpgradeStatus之前初始化完成
     */
    public static void init(Context context) {
        SPHelper.init(context);
    }

    /**
     * 初始化context
     * @param application
     * 使用的是ApplicationContext，所以该接口只需要在有context的对象中调用一次就可以
     * 必须在setAppUpgradeStatus之前初始化完成
     */
    public static void init(Application application) {
        SPHelper.init(application.getApplicationContext());
    }

    /**
     * 设置app的是否可以升级的接口
     * @param canUpgrade
     */
    public static void setAppUpgradeStatus(boolean canUpgrade) {
        SPHelper.save(ConstantUtil.KEY, canUpgrade);
    }

    /**
     * 获取app的是否可以升级的接口
     */
    public static boolean getAppUpgradeStatus() {
        return SPHelper.getBoolean(ConstantUtil.KEY, true);
    }

    /**
     * 设置设备的ID
     * call from android main program
     * @param deviceID
     */
    public static void setDeviceID(String deviceID) {
        SPHelper.saveString(ConstantUtil.KEY_DEVICE_ID, deviceID);
    }

    /**
     * 获取设备ID
     */
    public static String getDeviceIDFromMainProgram() {
        return SPHelper.getString(ConstantUtil.KEY_DEVICE_ID, "");
    }

    /**
     * 设置dfu设备mac地址
     * call from android main program
     * @param address
     */
    public static void setDfuMacAddress(String address) {
        SPHelper.saveString(ConstantUtil.KEY_DFU_MAC_ADDRESS, address);
    }

    /**
     * 获取dfu设备mac地址
     */
    public static String getDfuMacAddress() {
        return SPHelper.getString(ConstantUtil.KEY_DFU_MAC_ADDRESS, "");
    }
}
