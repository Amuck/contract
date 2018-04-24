package com.damao.tongxunlu.rxbus;

/**
 * Created by chenlong on 2017/3/10.
 * RxBus 的一些共有常量定义
 */

public class RxConstants {
    /**
     * when regist success close the login activity
     */
    public final static int REGIST_SUCCESS_CODE = 1001;
    public final static String REGIST_SUCCESS = "regist_success";
    public final static String RECHARGE_SUCCESS = "recharge_success";
    public final static String SINGLE_LOGIN = "single_login";

    /**
     * show the minefragment after regist success
     */
    public final static int REGIST_BACK_CODE = 1004;

    /**
     * when forget password reset success
     */
    public final static int FORGETPW_SUCCESS_CODE = 1003;
    public final static String FORGETPWD_SUCCESS = "forget_success";

    /**
     * operate gesture finish
     */
    public final static int GESTURE_FINISH_CODE = 1002;
    public final static String GESTURE_CREATE_SUCCESS = "gesture_create_success";
    public final static String GESTURE_CREATE_CANCEL = "gesture_create_cancel";
    public final static String GESTURE_CLOSE_SUCCESS = "gesture_close_success";
    public final static String GESTURE_CLOSE_CANCEL = "gesture_close_cancel";

    /**
     * 刷新用户信息界面
     */
    public final static int REFRESH_ACCOUNT_INFO_CODE = 1004;
    /**
     * user test finish
     */
    public final static String USER_TEST_SUCCESS = "uesr_test_success";
    /**
     * user reset login pwd finish
     */
    public final static String RESET_PWD_SUCCESS = "reset_pwd_success";
    /**
     * user reset pay pwd finish
     */
    public final static String RESET_PAY_PWD_SUCCESS = "reset_pay_pwd_success";
    public final static String AUTO_INVEST_SUCCESS = "auto_invest_success";
    public final static String NO_PWD_SUCCESS = "no_pwd_success";
    /**
     * 刷新我的投资界面
     */
    public static final int REFRESH_NEED = 1006;
    /**
     * 立即投资，选中红包
     */
    public static final int SELECT_GIFT = 1007;
    /**
     * 绑定银行卡
     */
    public static final int ADD_CARD = 1008;
    public final static String ADD_CARD_SUCCESS = "add_card_success";
    /**
     * 修改预留手机号
     */
    public final static String REST_PHONE_SUCCESS = "reset_phone_success";
    /**
     * 投资后刷新投资详情界面
     */
    public static final int PROJECT_DETAIL = 1009;
    public final static String PROJECT_DETAIL_REFRESH = "project_detail_refresh";
    /**
     * 解锁界面
     */
    public static final int UNLOCK_GESTURE = 1010;
    public final static String FINISH_GESTURE = "finish_gisture";
    /**
     * 更新
     */
    public static final int UPDATE_APP = 1011;
    public final static String UPDATE_APP_FAILED = "update_failed";
    public final static String UPDATE_APP_SUCCESS = "update_success";
    /**
     * 投资
     */
    public static final int INVEST_RESULT = 1012;
    public final static String INVEST_RESULT_SUCCESS = "invest_success";
    /**
     * 年度账单
     */
    public static final int BILL_RESULT = 1013;
    public final static String BILL_RESULT_SUCCESS = "bill_success";

}
