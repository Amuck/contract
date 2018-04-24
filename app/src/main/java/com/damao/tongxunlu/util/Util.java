package com.damao.tongxunlu.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.damao.tongxunlu.BaseApplication;
import com.damao.tongxunlu.R;
import com.damao.tongxunlu.entity.AuthEntity;
import com.damao.tongxunlu.entity.BaseEntity;
import com.damao.tongxunlu.sp.AccountInfoSP;
import com.damao.tongxunlu.sp.SettingSharedpreferences;
import com.damao.tongxunlu.view.AppDialog;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import static android.content.Context.ACTIVITY_SERVICE;

public class Util {
    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
        /*
        移动号段：
        134 135 136 137 138 139 147 150 151 152 157 158 159 172 178 182 183 184 187 188
        联通号段：
        130 131 132 145 155 156 171 175 176 185 186
        电信号段：
        133 149 153 173 177 180 181 189
        虚拟运营商:
        170
        中国电信获得199号段，中国移动得到198号段，中国联通得到166号段。
         */
        String num = "[1][3456789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            // matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    /**
     * @param string
     * @return          返回""表示密码有效
     */
    public static String isEffectPass(String string) {
        if (TextUtils.isEmpty(string) || string.length() < 8) {
            return BaseApplication.getAppContext().getString(R.string.pass_too_shore);
        } else {
            String num = ".*[0-9].*";
            String letter = ".*[a-zA-Z].*";
            if (string.matches(num) && string.matches(letter)) {
                return "";
            }
            return BaseApplication.getAppContext().getString(R.string.pass_too_easy);
        }
    }

    /**
     * show toast
     *
     * @param msg
     */
    public static void showToast(String msg) {
        if (!TextUtils.isEmpty(msg))
            Toast.makeText(BaseApplication.getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(int id) {
            Toast.makeText(BaseApplication.getAppContext(), id, Toast.LENGTH_SHORT).show();
    }

    /**
     * get the corresponding dimens value
     *
     * @param context
     * @param org
     * @return
     */
    public static float getDimens(Context context, int org) {

        Resources res = context.getResources();

        // DIMEN_42PX
        int id = res.getIdentifier("DIMEN_" + org + "DP", "dimen", context.getPackageName());

        return res.getDimension(id);
    }

    /**
     * if has network
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) BaseApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null)
        {
            return false;
        }
        else
        {
            NetworkInfo networkinfo = manager.getActiveNetworkInfo();
            if (networkinfo != null && networkinfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    /**
     * check if not null
     *
     * @param reference
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * check if not null
     *
     * @param reference
     * @param errorMsg
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference, @Nullable Object errorMsg) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMsg));
        }
        return reference;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        String version = "1.0.1";
        try {
            PackageManager manager = BaseApplication.getAppContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BaseApplication.getAppContext().getPackageName(), 0);
            version = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }


    /**
     * 查看是自己下载更新还是启动对应的市场进行更新，TRUE：打开对应的市场去更新，false：没有对应市场，需要自己更新
     *
     * @param context
     * @param market
     * @return
     */
    public static boolean isDownloadAppByMarket(@NonNull Context context, String market) {
        boolean result = false;
        checkNotNull(context, "isDownloadAppByMarket context cannot be null!");
        if (TextUtils.isEmpty(market)) {
            return result;
        }

        // 检查是否有对应的市场
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        int size = infos.size();
        for (int i = 0; i < size; i++) {
            ActivityInfo activityInfo = infos.get(i).activityInfo;
            String packageName = activityInfo.packageName;
            // 获取应用市场的包名
            if (market.equals(packageName)) {
                result = true;
                break;
            }
        }

        if (result) {
            // 有就跳转到对应的市场
            try {
                Uri uri = Uri.parse("market://details?id=" + ConstanceUtil.PACKEGE_NAME);// app包名
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setPackage(market);
                context.startActivity(intent1);
                return true;
            } catch (Exception e) {
                return false;
            }

        } else {
            // 没有则直接下载
            return false;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static float limitValue(float a, float b) {
        float valve = 0;
        final float min = Math.min(a, b);
        final float max = Math.max(a, b);
        valve = valve > min ? valve : min;
        valve = valve < max ? valve : max;
        return valve;
    }

    /**
     * 获取字体大小
     *
     * @param context
     * @param dimenId
     * @return
     */
    public static int getTextSize(Context context, int dimenId) {
        return px2sp(context,  context.getResources().getDimensionPixelSize(dimenId));
    }

    /**
     * 拨打电话
     *
     * @param context
     * @param phone
     */
    public static void call(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse(ConstanceUtil.TEL + phone);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 复制文本到剪切板
     *
     * @param copiedText
     */
    public static void copyTxt(Context context, String copiedText) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, copiedText));
        showToast(context.getString(R.string.copy_msg));
    }

    /**
     * 关闭键盘
     */
    public static void hideKeyBoard(Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive() && activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 获取颜色
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return ContextCompat.getColor(BaseApplication.getAppContext(), colorId);
    }

    /**
     * @param drawableId
     * @return
     */
    public static Drawable getDrawable(int drawableId) {
        return ContextCompat.getDrawable(BaseApplication.getAppContext(), drawableId);
    }

    /**
     * @return      屏幕宽度
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager)BaseApplication.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth();
    }

    /**
     * @return      屏幕高度
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager)BaseApplication.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getHeight();
    }

    public static  int getStatusBarHeight() {
        Resources resources = BaseApplication.getAppContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 拨打客服电话
     */
    public static void showCallDialog(Context context) {
        AppDialog callDialog = new AppDialog(context, R.style.Translucent_NoTitle);
        callDialog.setDialogImg(R.drawable.accountsetting_redphone);
        callDialog.setContent(context.getString(R.string.phone_dlg));
        callDialog.setContentSize(Util.getTextSize(context, R.dimen.PX_26));
        callDialog.setOkTxt(context.getString(R.string.ok_dlg));
        callDialog.setOkListener(() -> {
            Util.call(context, context.getString(R.string.phone));
        });
        callDialog.show();
    }

    /**
     * 单独设置EditText控件中hint文本的字体大小，可能与EditText文字大小不同
     *
     * @param editText 输入控件
     * @param hintText hint的文本内容
     * @param textSize hint的文本的文字大小（以dp为单位设置即可）
     */
    public static void setHintTextSize(EditText editText, String hintText, int textSize) {
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString(hintText);
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(textSize, true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置hint
        editText.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }

    /**
     * 需要在getOsVersion后面拼接一个手机型号
     * @return      the auth info
     */
    public static String getAuth() {
        String authCode = getAuthCode();
        String time = TimeUtil.getAuthDateStr();
        String latitude = SettingSharedpreferences.getLATITUDE();
        String longitude = SettingSharedpreferences.getLONGITUDE();
        String ip = Util.getIPAddress(BaseApplication.getAppContext());
        String userId = SettingSharedpreferences.getUserId();
        if (TextUtils.isEmpty(userId))
            userId = "0";
        String tocken = SettingSharedpreferences.getTocken();
        String OsVersion = SettingSharedpreferences.getOsVersion();
        String imei = SettingSharedpreferences.getUUID();
        String AppVersion = SettingSharedpreferences.getAppVersion();
        if (TextUtils.isEmpty(tocken))
            tocken = "0";
        AuthEntity authEntity = new AuthEntity.Builder(TextUtils.isEmpty(AppVersion) ? "0" : AppVersion,
                time, authCode)
                .imei(TextUtils.isEmpty(imei) ? "0" : imei)
                .osVersion(TextUtils.isEmpty(OsVersion) ? "0" : OsVersion)
                .userId(userId)
                .token(tocken)
                .latitude(TextUtils.isEmpty(latitude) ? "0" : latitude)
                .longitude(TextUtils.isEmpty(longitude) ? "0" : longitude)
                .ip(TextUtils.isEmpty(ip) ? "0" : ip)
                .build();
        Gson gson = new Gson();
        return gson.toJson(authEntity);
    }

    public static String getAuthCode() {
        String key = "123456789";
        String userId = SettingSharedpreferences.getUserId();
        if (TextUtils.isEmpty(userId))
            userId = "0";
        String org = key + SettingSharedpreferences.getUUID() + userId + SettingSharedpreferences.getAppVersion()
                + TimeUtil.getAuthDateStr();
        return MD5Util.encrypt(org);
    }



    /**
     * 对字符串处理:将指定位置到指定位置的字符以星号代替
     *
     * @param content
     *            传入的字符串
     * @param begin
     *            开始位置
     * @param end
     *            结束位置
     * @return
     */
    public static String getStarString(String content, int begin, int end) {

        if (begin >= content.length() || begin < 0) {
            return content;
        }
        if (end >= content.length() || end < 0) {
            return content;
        }
        if (begin >= end) {
            return content;
        }
        String starStr = "";
        for (int i = begin; i < end; i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, begin) + starStr + content.substring(end, content.length());

    }

    /**
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替
     *
     * @param content
     *            传入的字符串
     * @param frontNum
     *            保留前面字符的位数
     * @param endNum
     *            保留后面字符的位数
     * @return 带星号的字符串
     */

    public  static String getStarString2(String content, int frontNum, int endNum) {

        if (TextUtils.isEmpty(content) || frontNum >= content.length() || frontNum < 0) {
            return content;
        }
        if (endNum >= content.length() || endNum < 0) {
            return content;
        }
        if (frontNum + endNum >= content.length()) {
            return content;
        }
        String starStr = "";
        for (int i = 0; i < (content.length() - frontNum - endNum); i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, frontNum) + starStr
                + content.substring(content.length() - endNum, content.length());

    }

    //获得独一无二的Psuedo ID
    public static String getUniquePsuedoID() {
        String serial;

        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**
     * 获取ip地址
     * @param context
     * @return
     */
    @Nullable
    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    /**
     * 设置标志位，部分界面需要下次进入后，重新请求数据。
     * 1.账户信息界面
     */
    public static void clearDataCache() {
        // 账户信息界面重新请求数据
        SettingSharedpreferences.saveBoolean(ConstanceUtil.CLEAR_ACCOUNT_INFO, true);
    }

    /**
     * 清空用户缓存
     */
    public static void clearUserCache() {
        SettingSharedpreferences.clearUserId();
        SettingSharedpreferences.clearTocken();
        AccountInfoSP.clear();
    }

    /**
     * 打印屏幕宽度等信息
     */
    public static String showmetric(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)
        Log.d("Util", "width= " + width + " , height= " + height + " , density= " + density + " ,densityDpi= " + densityDpi);
        Log.d("Util", "屏幕宽度（dp）：" + screenWidth);
        Log.d("Util", "屏幕高度（dp）：" + screenHeight);

        return "width= " + width + " , height= " + height + " , density= " + density + " ,densityDpi= " + densityDpi
                 + "\n" +  "屏幕宽度（dp）：" + screenWidth + "\n" +"屏幕高度（dp）：" + screenHeight;
    }


    /**
     * 判断某一个类是否存在任务栈里面
     * @return
     */
    public static boolean isExsitMianActivity(Class cls){
        Intent intent = new Intent(BaseApplication.getAppContext(), cls);
        ComponentName cmpName = intent.resolveActivity(BaseApplication.getAppContext().getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) BaseApplication.getAppContext().getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                    flag = true;
                    break;  //跳出循环，优化效率
                }
            }
        }
        return flag;
    }


    /**
     * 检测通知栏权限是否开启
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isNotificationEnabled(Context context) {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
  /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 网络请求是否成功, 失败则显示对应消息
     * 当code为空时，不需要显示msg
     * @param baseEntity
     * @return      空串为成功， 其他为失败原因
     */
    public static String isSuccess(BaseEntity baseEntity, Context context) {
        String result;
        if (null == baseEntity) {
            return BaseApplication.getAppContext().getString(R.string.request_error);
        }
        if ("000000".equals(baseEntity.getCode()) || "200".equals(baseEntity.getCode())) {
            result = "";
        } else {
            result = baseEntity.getMsg();
//            if (!TextUtils.isEmpty(result) && result.contains(BaseApplication.getAppContext().getString(R.string.not_login_error))) {
//                // 需要跳转登录界面
//                context.startActivity(new Intent(context, LoginActivity.class));
//                result = BaseApplication.getAppContext().getString(R.string.not_login_msg);
//            }
        }
        // 单点登录
//        if(TextUtils.isEmpty(baseEntity.getCode()) && ConstanceUtil.SINGLE_MSG.equals(result)) {
//            Util.clearUserCache();
//            showKickOutDlg(context);
//            result = "";
//        }
        return result;
    }
}
