package com.damao.tongxunlu;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.damao.tongxunlu.service.X5CorePreLoadService;
import com.damao.tongxunlu.sp.SettingSharedpreferences;
import com.damao.tongxunlu.util.Util;

import java.util.List;


/**
 * @author chenlong
 *         <p>
 *         the application used in this app
 */
//public class BaseApplication extends MultiDexApplication {
public class BaseApplication extends Application {
    /**
     * 投资成功需要的参数
     */
    public static String REQUEST_NO = "";
    /**
     * 圆梦活动开始日期
     */
    public static String YM_START_TIME = "";
    /**
     * 圆梦活动结束日期
     */
    public static String YM_END_TIME = "";
    private static final String TAG = BaseApplication.class.getName();

    private static BaseApplication mInstance;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();

        loadLib();
        initAuth();
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }

    private void initAuth() {
        // get uuid
//        if (TextUtils.isEmpty(SettingSharedpreferences.getUUID())) {
//            SettingSharedpreferences.setUUID(Util.getUniquePsuedoID());
//        }
        // get osVersion
        SettingSharedpreferences.setOsVersion(Util.getSystemVersion() + "&" + Build.BRAND + "&" + Build.MODEL);
        // get app version
        SettingSharedpreferences.setAppVersion(Util.getVersion());
    }

    /**
     * 加载lib
     */
    public void loadLib() {
        checkFingerprintManager();
        preInitX5Core();
    }

    public  String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return context;
    }

    /**
     * 初始化X5内核
     */
    private void preInitX5Core() {
        //预加载x5内核
        Intent intent = new Intent(this, X5CorePreLoadService.class);
        startService(intent);
    }
    /**
     * 检查是否有指纹模块
     */
    private void checkFingerprintManager() {
        if (SettingSharedpreferences.hasCheckedFingerAPI()) { // 检查是否存在该值，不必每次都通过反射来检查
            return;
        }
        boolean result;
        try {
            Class.forName("android.hardware.fingerprint.FingerprintManager"); // 通过反射判断是否存在该类
            result = true;
        } catch (ClassNotFoundException e) {
            result = false;
            e.printStackTrace();
        }
        SettingSharedpreferences.setFingerAPI(result);
    }

}
