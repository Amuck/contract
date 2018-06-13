package com.damao.tongxunlu.util;

import android.provider.CallLog;

/**
 * Created by chenlong on 2017/6/1.
 * <p>
 * 常量类
 */

public class ConstanceUtil {
    public static final String PACKEGE_NAME = "com.damao.tongxunlu";
    public static final String TEL = "tel:";
    public static final String OS_TYPE = "Android";
    public static final String WAP_TYPE = "WAP";
    /*------ sp相关 -----------------------*/
    /**
     * 是否有指纹模块
     */
    public static final String HAS_FINGERPRINT_API = "hasFingerPrintApi";
    /**
     * 软件设置存放的sp
     */
    public static final String SETTINGS = "settings";
    /**
     * 是否是再次打开软件，如果是的话，某些数据需要重新请求
     * true: 重新请求， false：不需要请求
     */
    public static final String CLEAR_ACCOUNT_INFO = "clear_account_info";
    /**
     * user_id
     */
    public static final String USER_ID = "user_id";
    /**
     * user_id用于手势密码
     */
    public static final String USER_ID_GES = "user_id_ges";
    /**
     * tocken
     */
    public static final String TOCKEN = "tocken";
    /**
     * 纬度
     */
    public static final String LATITUDE = "latitude";
    /**
     * 经度
     */
    public static final String LONGITUDE = "longitude";
    /**
     * 是否打开了手势密码
     */
    public static final String OPEN_GES = "open_ges";
    /**
     * 是否隐藏手势密码的轨迹， 和手势密码开关联动
     */
    public static final String HIDE_GES_TRACK = "hide_track";
    /**
     * 是否是首次启动
     */
    public static final String IS_FIRST_START = "lastversion";
    /**
     * UUID
     */
    public static final String UUID = "uuid";
    /**
     * 操作系统版本
     */
    public static final String OS_VERSION = "osversion";
    /**
     * APP版本
     */
    public static final String APP_VERSION = "app_version";
    /**
     * 用户账户信息存放的sp
     */
    public static final String ACCOUNT_INFO = "account_info";
    /**
     * 缓存的userid
     */
    public static final String CACHE_USER_ID = "cache_user_id";
    /**
     * 真实用户名
     */
    public static final String CACHE_REAL_NAME = "cache_real_name";
    /**
     * 头像url
     */
    public static final String CACHE_ICON = "cache_icon";
    /**
     * 性别,0:女1:男;空：未实名
     */
    public static final String CACHE_SEX = "cache_sex";
    /**
     * 生日
     */
    public static final String CACHE_BRITHDAY = "cache_birthday";
    /**
     * 存管账户id
     */
    public static final String CACHE_CARDNBRID = "cache_cardnbrid";
    /**
     * 银行卡尾号
     */
    public static final String CACHE_BANK_TAILNUM = "cache_bankTailnum";
    /**
     * 银行卡名称
     */
    public static final String CACHE_BANK_NAME = "cache_bankname";
    /**
     * 银行卡图片url
     */
    public static final String CACHE_BANK_URL = "cache_bank_url";
    /**
     * 预留手机号
     */
    public static final String CACHE_PHONE = "cache_phone";
    /**
     * 支付密码是否存在,存在“1”,不存在“0”
     */
    public static final String CACHE_PAY_PASSWORD = "cache_payPassword";
    /**
     * 自动出借授权是否开通
     */
    public static final String CACHE_AUTO_INVEST = "cache_autoInvest";
    /**
     * 免密授权是否开通
     */
    public static final String NO_PWD = "no_pwd";
    /**
     * 用户测评后的等级
     */
    public static final String CACHE_USER_LEVEL = "cache_userlevel";
    /**
     * 用户测评描述
     */
    public static final String CACHE_USER_DESCRIPTION = "cache_user_description";
    /**
     * 用户测评限额
     */
    public static final String CACHE_USER_MONEY = "cache_user_money";
    /**
     * 用户余额
     */
    public static final String USEFUL_MONEY = "useful_money";
    /**
     * 投资金额
     */
    public static final String INVEST_MONEY = "invest_money";
    /**
     * 放款回款消息提醒开关
     */
    public static final String NOTIFY_INVEST = "notify_invest";
    /**
     * 红包过期消息提醒开关
     */
    public static final String NOTIFY_GIFT = "notify_gift";
    /**
     * 上一次刷新项目列表的时间
     */
    public static final String LAST_REFRESH = "last_refresh";
    /**
     * 是否打开过年度账单
     */
    public static final String BILL_OPEN = "bill_open";
    /* --------------------------------------*/


    /*------ 下载相关 -----------------------*/
    public static final String UPDATA_APP = "downloadurl";
    public static final String DOWNLOADPATH = "/apk/";//下载路径，如果不定义自己的路径，6.0的手机不自动安装
    public static final String APK_NAME = "toxunlu.apk";
    public static final String DOWNLOAD_TITLE = "大猫通讯录";
    /* --------------------------------------*/

    /**
     * 单点登录
     */
    public static final String SINGLE_MSG = "true";

    /*------ 网络相关 -----------------------*/
    /**
     * 服务器地址
     */

    // 测试
    public static final String API_HOST = "http://test.jinrongweb.com/app/";

    /*------ Glide相关 -----------------------*/
    // 图片缓存最大容量，150M
    public static final int GLIDE_CATCH_SIZE = 150 * 1000 * 1000;

    // 图片缓存子目录
    public static final String GLIDE_CARCH_DIR = "image_catch";
    /* --------------------------------------*/

    /*------ contract type -----------------------*/
    /**
     * 全部
     */
    public static final int CONTRACT_ALL = -1;
    /**
     * 未接来电
     */
    public static final int CONTRACT_NOT = CallLog.Calls.MISSED_TYPE;
    /**
     * 常用联系人
     */
    public static final int CONTRACT_ALLWAYS = -1;
    /* --------------------------------------*/
}
