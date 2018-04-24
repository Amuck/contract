package com.damao.tongxunlu.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.damao.tongxunlu.BaseApplication;
import com.damao.tongxunlu.util.ConstanceUtil;


/**
 * 设置相关
 */
public class SettingSharedpreferences {

    /**
     * 保存指纹模块的属性
     * @param hasFingerAPI
     */
    public static void setFingerAPI(boolean hasFingerAPI){
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        Editor editor = pref.edit();
        editor.putBoolean(ConstanceUtil.HAS_FINGERPRINT_API, hasFingerAPI);
        editor.apply();
    }

    /**
     * @return      是否检查过指纹模块
     */
    public static boolean hasCheckedFingerAPI() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );

        return pref.contains(ConstanceUtil.HAS_FINGERPRINT_API);
    }

    /**
     * @return      是否含有指纹模块
     */
    public static boolean hasFingerAPI() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        return pref.getBoolean(ConstanceUtil.HAS_FINGERPRINT_API, false);
    }

    /**
     * 保存属性
     * @param key
     * @param value
     */
    public static void saveString(String key, String value) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(String key, String defaultValue){
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        return pref.getString(key, defaultValue);
    }

    public static void saveBoolean(String key, Boolean value) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static Boolean getBoolean(String key, boolean defaultValue){
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        return pref.getBoolean(key, defaultValue);
    }

    public static void clear(){
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 是否是首次启动
     * @return      上次关闭引导页时的版本号，为空则是首次安装首次启动
     */
    public static String isFirstStart() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        return pref.getString(ConstanceUtil.IS_FIRST_START, "");
    }

    /**
     * 首次启动完成
     */
    public static void firstStartFinish(String version) {
        saveString(ConstanceUtil.IS_FIRST_START, version);
    }

    /**
     * 用户id
     * @param userId
     */
    public static void setUserId(String userId) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.USER_ID, userId);
        editor.apply();
    }

    /**
     * 清除userid
     */
    public static void clearUserId() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.USER_ID, "");
        editor.apply();
    }

    /**
     * @return      USER_ID
     */
    public static String getUserId() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        return pref.getString(ConstanceUtil.USER_ID, "");
    }
    /**
     * 用来判断手势密码的用户id
     * @param userId
     */
    public static void setUserIdForges(String userId) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.USER_ID_GES, userId);
        editor.apply();
    }

    /**
     * 清除用来判断手势密码的用户id
     */
    public static void clearUserIdForges() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.USER_ID_GES, "");
        editor.apply();
    }

    /**
     * @return      用来判断手势密码的用户id
     */
    public static String getUserIdForges() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        return pref.getString(ConstanceUtil.USER_ID_GES, "");
    }

    /**
     * tocken
     * @param tocken
     */
    public static void setTocken(String tocken) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.TOCKEN, tocken);
        editor.apply();
    }

    /**
     * 清除Tocken
     */
    public static void clearTocken() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.TOCKEN, "");
        editor.apply();
    }

    /**
     * @return      Tocken
     */
    public static String getTocken() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        return pref.getString(ConstanceUtil.TOCKEN, "");
    }

    /**
     * uuid(现改为友盟的device token)
     * @param uuid
     */
    public static void setUUID(String uuid) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.UUID, uuid);
        editor.apply();
    }
    /**
     * @return      uuid
     */
    public static String getUUID() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        return pref.getString(ConstanceUtil.UUID, "");
    }

    /**
     * osVersion
     * @param osVersion
     */
    public static void setOsVersion(String osVersion) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.OS_VERSION, osVersion);
        editor.apply();
    }
    /**
     * @return      osVersion
     */
    public static String getOsVersion() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        return pref.getString(ConstanceUtil.OS_VERSION, "");
    }
    /**
     * appVersion
     * @param appVersion
     */
    public static void setAppVersion(String appVersion) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.APP_VERSION, appVersion);
        editor.apply();
    }
    /**
     * @return      appVersion
     */
    public static String getAppVersion() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        return pref.getString(ConstanceUtil.APP_VERSION, "");
    }

    /**
     * @return      latitude
     */
    public static String getLATITUDE() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        return pref.getString(ConstanceUtil.LATITUDE, "");
    }

    /**
     * latitude
     */
    public static void setLATITUDE(String latitude) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.LATITUDE, latitude);
        editor.apply();
    }
    /**
     * @return      longitude
     */
    public static String getLONGITUDE() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        return pref.getString(ConstanceUtil.LONGITUDE, "");
    }

    /**
     * longitude
     */
    public static void setLONGITUDE(String longitude) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.SETTINGS, Context.MODE_PRIVATE  );
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.LONGITUDE, longitude);
        editor.apply();
    }

}
