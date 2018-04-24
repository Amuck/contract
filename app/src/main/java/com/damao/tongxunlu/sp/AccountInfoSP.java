package com.damao.tongxunlu.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.damao.tongxunlu.BaseApplication;
import com.damao.tongxunlu.util.ConstanceUtil;


/**
 * Created by chenlong on 2017/8/23.
 * 账户相关
 */

public class AccountInfoSP {
    /**
     * 自动出借授权是否开通
     *
     * @param state
     */
    public static void setAutoInvest(boolean state) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putBoolean(ConstanceUtil.CACHE_AUTO_INVEST, state);
        editor.apply();
    }

    /**
     * @return 自动出借授权是否开通
     */
    public static boolean hasAutoInvest() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getBoolean(ConstanceUtil.CACHE_AUTO_INVEST, false);
    }

    /**
     * 免密授权是否开通
     *
     * @param state
     */
    public static void setNoPwd(boolean state) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putBoolean(ConstanceUtil.NO_PWD, state);
        editor.apply();
    }

    /**
     * @return 免密授权是否开通
     */
    public static boolean hasNoPwd() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getBoolean(ConstanceUtil.NO_PWD, false);
    }

    /**
     * 平台手机号
     *
     * @param phone
     */
    public static void setPlantPhone(String phone) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.CACHE_USER_ID, phone);
        editor.apply();
    }

    /**
     * @return 平台手机号
     */
    public static String getPlantPhone() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getString(ConstanceUtil.CACHE_USER_ID, "");
    }

    /**
     * 真实用户名
     *
     * @param realName
     */
    public static void setRealName(String realName) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.CACHE_REAL_NAME, realName);
        editor.apply();
    }

    /**
     * @return 真实用户名
     */
    public static String getRealName() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getString(ConstanceUtil.CACHE_REAL_NAME, "");
    }

    /**
     * 头像url
     *
     * @param icon
     */
    public static void setIcon(String icon) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.CACHE_ICON, icon);
        editor.apply();
    }

    /**
     * @return 头像url
     */
    public static String getIcon() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getString(ConstanceUtil.CACHE_ICON, "");
    }

    /**
     * 性别,0:女1:男;空：未实名
     *
     * @param sex
     */
    public static void setSex(String sex) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.CACHE_SEX, sex);
        editor.apply();
    }

    /**
     * @return 性别, 0:女1:男;空：未实名
     */
    public static String getSex() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getString(ConstanceUtil.CACHE_SEX, "");
    }

    /**
     * 存管账户id
     *
     * @param cardnbrid
     */
    public static void setCardnbrid(String cardnbrid) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.CACHE_CARDNBRID, cardnbrid);
        editor.apply();
    }

    /**
     * @return 存管账户id
     */
    public static String getCardnbrid() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getString(ConstanceUtil.CACHE_CARDNBRID, "");
    }

    /**
     * 生日
     *
     * @param birthday
     */
    public static void setBirthday(String birthday) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.CACHE_BRITHDAY, birthday);
        editor.apply();
    }

    /**
     * @return 生日
     */
    public static String getBirthday() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getString(ConstanceUtil.CACHE_BRITHDAY, "");
    }

    /**
     * 银行卡尾号
     *
     * @param bankTailnum
     */
    public static void setBankTailnum(String bankTailnum) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.CACHE_BANK_TAILNUM, bankTailnum);
        editor.apply();
    }

    /**
     * @return 银行卡尾号
     */
    public static String getBankTailnum() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getString(ConstanceUtil.CACHE_BANK_TAILNUM, "");
    }

    /**
     * 银行名称
     *
     * @param bankname
     */
    public static void setBankname(String bankname) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.CACHE_BANK_NAME, bankname);
        editor.apply();
    }

    /**
     * @return 银行名称
     */
    public static String getBankname() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getString(ConstanceUtil.CACHE_BANK_NAME, "");
    }

    /**
     * 银行url
     *
     * @param url
     */
    public static void setBankUrl(String url) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.CACHE_BANK_URL, url);
        editor.apply();
    }

    /**
     * @return 银行url
     */
    public static String getBankUrl() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getString(ConstanceUtil.CACHE_BANK_URL, "");
    }

    /**
     * 预留手机号
     *
     * @param phone
     */
    public static void setPhone(String phone) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.CACHE_PHONE, phone);
        editor.apply();
    }

    /**
     * @return 预留手机号
     */
    public static String getPhone() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getString(ConstanceUtil.CACHE_PHONE, "");
    }

    /**
     * 支付密码是否存在,存在“1”,不存在“0”
     *
     * @param payPassword
     */
    public static void setPayPassword(String payPassword) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.CACHE_PAY_PASSWORD, payPassword);
        editor.apply();
    }

    /**
     * @return 支付密码是否存在, 存在“1”,不存在“0”
     */
    public static String getPayPassword() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getString(ConstanceUtil.CACHE_PAY_PASSWORD, "");
    }

    /**
     * 用户测评后的等级
     *
     * @param userlevel
     */
    public static void setUserlevel(String userlevel) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.CACHE_USER_LEVEL, userlevel);
        editor.apply();
    }

    /**
     * @return 用户测评后的等级
     */
    public static String getUserlevel() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getString(ConstanceUtil.CACHE_USER_LEVEL, "");
    }

    /**
     * 用户测评描述
     *
     * @param userleveldes
     */
    public static void setUserleveldes(String userleveldes) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.CACHE_USER_DESCRIPTION, userleveldes);
        editor.apply();
    }

    /**
     * @return 用户测评描述
     */
    public static String getUserleveldes() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getString(ConstanceUtil.CACHE_USER_DESCRIPTION, "");
    }

    /**
     * 用户测评限额
     *
     * @param userlevelMoney 5万元
     */
    public static void setUserleveMoneyl(String userlevelMoney) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.CACHE_USER_MONEY, userlevelMoney);
        editor.apply();
    }

    /**
     * @return 用户测评限额
     */
    public static String getUserlevelMoney() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getString(ConstanceUtil.CACHE_USER_MONEY, "");
    }

    /**
     * 用户余额
     */
    public static void setusefulMoney(String usefulMoney) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.USEFUL_MONEY, usefulMoney);
        editor.apply();
    }

    /**
     * @return 用户余额
     */
    public static String getusefulMoney() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getString(ConstanceUtil.USEFUL_MONEY, "");
    }

    /**
     * 投资金额
     */
    public static void setInvestMoney(String investMoney) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(ConstanceUtil.INVEST_MONEY, investMoney);
        editor.apply();
    }

    /**
     * @return 投资金额
     */
    public static String getInvestMoney() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getString(ConstanceUtil.INVEST_MONEY, "");
    }

    /**
     * 放款回款消息提醒开关
     */
    public static void setNotifyInvest(boolean isOpen) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putBoolean(ConstanceUtil.NOTIFY_INVEST, isOpen);
        editor.apply();
    }

    /**
     * @return 放款回款消息提醒开关
     */
    public static boolean getNotifyInvest() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getBoolean(ConstanceUtil.NOTIFY_INVEST, true);
    }

    /**
     * 红包过期消息提醒开关
     */
    public static void setNotifyGift(boolean isOpen) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putBoolean(ConstanceUtil.NOTIFY_GIFT, isOpen);
        editor.apply();
    }

    /**
     * @return 红包过期消息提醒开关
     */
    public static boolean getNotifyGift() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getBoolean(ConstanceUtil.NOTIFY_GIFT, true);
    }

    /**
     * 是否打开过年度账单
     */
    public static void setBillOpen(boolean isOpen) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putBoolean(ConstanceUtil.BILL_OPEN, isOpen);
        editor.apply();
    }

    /**
     * @return 是否打开过年度账单
     */
    public static boolean getBillOpen() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getBoolean(ConstanceUtil.BILL_OPEN, false);
    }

    /**
     * 上一次刷新项目列表的时间
     */
    public static void setLastRefreshProjectList(long time) {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putLong(ConstanceUtil.LAST_REFRESH, time);
        editor.apply();
    }

    /**
     * @return 上一次刷新项目列表的时间
     */
    public static long getLastRefreshProjectList() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        return pref.getLong(ConstanceUtil.LAST_REFRESH, 0);
    }

    /**
     * 清空
     */
    public static void clear() {
        SharedPreferences pref = BaseApplication.getAppContext().getSharedPreferences(ConstanceUtil.ACCOUNT_INFO, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }
}
