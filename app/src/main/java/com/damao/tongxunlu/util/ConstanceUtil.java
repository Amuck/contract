package com.damao.tongxunlu.util;

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
    /**
     * 首页信息存放的sp
     */
    public static final String HOME_INFO = "home_info";
    /**
     * 最新的一条公告id
     */
    public static final String lastNewsid = "lastNewsid";
    /**
     * 最新的一条公告标题
     */
    public static final String lastNewstitle = "lastNewstitle";
    /**
     * 首页banner
     */
    public static final String bannerlist = "bannerlist";
    /**
     * 标的的加息利率
     */
    public static final String investCoupon = "investCoupon";
    /**
     * 标的状态
     */
    public static final String investStatus = "investStatus";
    /**
     * 标的名称
     */
    public static final String investName = "investName";
    /**
     * 标的的ID
     */
    public static final String investId = "investId";
    /**
     * 标标的的借款利率
     */
    public static final String investRate = "investRate";
    /**
     * 标的的借款金额
     */
    public static final String investTotalMoney = "investTotalMoney";
    /**
     * 标的的借款期限
     */
    public static final String investDeadline = "investDeadline";
    /* --------------------------------------*/


    /*------ 验证码种类 -----------------------*/
    /**
     * 注册
     */
    public static final String IDENTIFY_REGIST = "regist";
    /**
     * 重置登陆密码
     */
    public static final String IDENTIFY_RESET_PWD = "reSetPwd";
    /**
     * 设置支付密码
     */
    public static final String IDENTIFY_PAY_PWD = "setPayPwd";
    /**
     * 修改平台手机号（验证原手机号）
     */
    public static final String IDENTIFY_CHECK_OLD = "checkOldPhone";
    /**
     * 修改平台手机号（添加新手机号）
     */
    public static final String IDENTIFY_SET_NEW = "setNewPhone";
    /**
     * 快捷充值
     */
    public static final String IDENTIFY_RECHARGE = "recharge";
    /**
     * 忘记密码
     */
    public static final String IDENTIFY_FORGET_PWD = "forgetPwd";
    /**
     * 圆梦账户转入
     */
    public static final String IDENTIFY_YM_RECHARGE = "ymRecharge";
    /**
     * 圆梦账户转出
     */
    public static final String IDENTIFY_YM_CASH = "ymCash";
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
    public static final String OTHER_HOST = "http://test.jinrongweb.com/new-info-core/";
    public static final String ACTIVE_URL = "http://m.test.jinrongweb.com/account/center";
    public static final String WEB_HOST = "http://test.jinrongweb.com/app/";
    public static final String YEAR_BILL = "http://m.test.jinrongweb.com/yearBill/getUser2017LastDinner";

    /*------ Glide相关 -----------------------*/
    // 图片缓存最大容量，150M
    public static final int GLIDE_CATCH_SIZE = 150 * 1000 * 1000;

    // 图片缓存子目录
    public static final String GLIDE_CARCH_DIR = "image_catch";
    /* --------------------------------------*/


    /*------ 银行存管开户 -----------------------*/
    /**
     * 注册成功跳转过来
     */
    public static final String FROM_REGIST = "from_regist";
    /**
     * 非注册界面跳转
     */
    public static final String FROM_OTHER = "from_other";
    public static final String FROM_KEY = "from_key";
    /* --------------------------------------*/


    /*------ 收益明细 -----------------------*/
    /**
     * 收益类型的key
     */
    public static final String INCOME_KIND_KEY = "income_kind_key";
    /**
     * 项目收益
     */
    public static final String INCOME_KIND_PROJECT = "income_kind_project";
    /**
     * 项目加息收益
     */
    public static final String INCOME_KIND_ADD = "income_kind_add";
    /**
     * 红包收益
     */
    public static final String INCOME_KIND_GIFT = "income_kind_gift";
    /**
     * 合伙人奖励
     */
    public static final String INCOME_KIND_FRIEND = "income_kind_friend";
    /**
     * 项目收益状态， 已收
     */
    public static final String INCOME_STATUS_GET = "已收";
    /**
     * 项目收益状态， 待收
     */
    public static final String INCOME_STATUS_WAIT = "待收";
    /**
     * 项目红包种类， 加息券
     */
    public static final String INCOME_GIFT_TYPE_ADD = "加息券";
    /**
     * 项目红包种类， 返现券
     */
    public static final String INCOME_GIFT_TYPE_BACK = "返现券";
    /**
     * 项目红包种类， 加息券
     */
    public static final String INVEST_GIFT_TYPE_ADD = "JXQ";
    /**
     * 项目红包种类， 返现券
     */
    public static final String INVEST_GIFT_TYPE_BACK = "FXQ";
    /**
     * 项目红包种类， 现金券
     */
    public static final String INCOME_GIFT_TYPE_CASH = "现金券";
    /* --------------------------------------*/

    /*------ 使用红包 -----------------------*/
    public static final String GIFT_KEY = "gift_key";
    /**
     * 从账户总览跳转
     */
    public static final String GIFT_ACCOUNT = "gift_account";
    /**
     * 从投资界面跳转
     */
    public static final String GIFT_INVEST = "gift_invest";
    /* --------------------------------------*/

    /*------ 项目分类 -----------------------*/
    /**
     * 全部项目
     */
    public static final int PROJECT_TYPE_ALL = 0;
    /**
     * 优质项目
     */
    public static final int PROJECT_TYPE_GOOD = 1;
    /**
     * 债权转让
     */
    public static final int PROJECT_TYPE_ASSIGN = 2;
    /**
     * 项目的状态: 默认
     */
    public static final String PROJECT_STATUS_DEFAULT = "0";
    /**
     * 项目的状态: 筹款中
     */
    public static final String PROJECT_STATUS_GETTING = "1";
    /**
     * 项目的状态: 待放款
     */
    public static final String PROJECT_STATUS_WAITTING = "2";
    /**
     * 项目的状态: 回款中
     */
    public static final String PROJECT_STATUS_BACK = "3";
    /* --------------------------------------*/
    /*------ 标的项目列表状态 -----------------------*/
    /**
     * 项目的状态: 标的项目列表状态key
     */
    public static final String PROJECT_LIST_STATUS_KEY = "project_list_status_key";
    /**
     * 项目的状态: 筹款中
     */
    public static final String PROJECT_LIST_STATUS_GETTING = "筹款中";
    /**
     * 项目的状态: 待放款
     */
    public static final String PROJECT_LIST_STATUS_WAITTING = "待放款";
    /**
     * 项目的状态: 回款中
     */
    public static final String PROJECT_LIST_STATUS_BACK = "回款中";
    /**
     * 项目的状态: 已结束
     */
    public static final String PROJECT_LIST_STATUS_OVER = "已结束";
    /* --------------------------------------*/

    /*------ 交易明细列表状态 -----------------------*/
    /**
     * 项目的状态: 全部
     */
    public static final String DEAL_LIST_STATUS_ALL = "all";
    /**
     * 项目的状态: 充值
     */
    public static final String DEAL_LIST_STATUS_RECHARGE = "reCharge";
    /**
     * 项目的状态: 提现
     */
    public static final String DEAL_LIST_STATUS_WITHDRAW = "withDraw";
    /**
     * 项目的状态: 投资
     */
    public static final String DEAL_LIST_STATUS_INVEST = "invest";
    /**
     * 项目的状态: 投资
     */
    public static final String DEAL_LIST_STATUS_YM_INVEST = "ymInvest";
    /**
     * 项目的状态: 回款
     */
    public static final String DEAL_LIST_STATUS_MONEY = "backMoney";
    /**
     * 项目的状态: 平台奖励
     */
    public static final String DEAL_LIST_STATUS_AWARD = "award";
    /**
     * 项目的状态: 提现手续费
     */
    public static final String DEAL_LIST_STATUS_WITHDRAW_FEE = "withDrawFee";
    /**
     * 项目的状态: 债权出让
     */
    public static final String DEAL_LIST_STATUS_ZHAI_QUAN = "zhaiquan";
    /**
     * -
     */
    public static final String FREEZE = "freeze";
    /**
     * -
     */
    public static final String TO_BALANCE = "to_balance";
    /**
     * +
     */
    public static final String DREAM_REWARD = "dream_reward";
    /**
     * +
     */
    public static final String RETURN_CASH = "return_cash";
    /**
     * +
     */
    public static final String RETURN_CASH_REFEREE = "return_cash_referee";
    /**
     * +
     */
    public static final String TI_BALANCE = "ti_balance";

    /* --------------------------------------*/

    /*------ 还款/回款 状态 -----------------------*/
    /**
     * 还款状态: 待放款
     */
    public static final String WAIT_FOR_PAY = "wait_for_pay";
    /**
     * 还款状态: 已完成
     */
    public static final String PAY_OVER = "pay_over";
    /* --------------------------------------*/

    /*------ ID -----------------------*/
    /**
     * 标的id
     */
    public static final String LOAN_ID = "loan_id";
    /* --------------------------------------*/

    /*------ ID -----------------------*/
    /**
     * 非债转标
     */
    public static final String LOAN_TYPE_NOR = "false";
    /**
     * 债转标
     */
    public static final String LOAN_TYPE_TR = "true";
    /**
     * 直融标
     */
    public static final String LOAN_TYPE_ZR = "zhirong";
    /* --------------------------------------*/

    /*------ 红包类型 -----------------------*/
    /**
     * 全部
     */
    public static final int GIFT_TYPE_USEFUL = 1;
    /**
     * 未接
     */
    public static final int GIFT_TYPE_CASH = 2;
    /**
     * 陌生
     */
    public static final int GIFT_TYPE_HISTORY = 3;
    /**
     * 已使用
     */
    public static final String GIFT_STATUS_USED = "used";
    /**
     * 已过期
     */
    public static final String GIFT_STATUS_DIS = "disable";
    /* --------------------------------------*/
     /*------ webview 需要跳转的页面 -----------------------*/
    /**
     * 打开注册界面
     */
    public static final String OPEN_REGIST = "open_regist";
    /**
     * 打开投资详情界面
     */
    public static final String OPEN_INVEST_RESULT = "open_invest_result";
    /* --------------------------------------*/
     /*------ 检查更新 -----------------------*/
    /**
     * 强制更新
     */
    public static final int MUST_UPDATE = 1;
    /**
     * 选择更新
     */
    public static final int HAVE_UPDATE = 2;
    /**
     * 无需更新
     */
    public static final int NOT_UPDATE = 3;
    /* --------------------------------------*/

     /*------ 圆梦计划类型 -----------------------*/
    /**
     * 购车
     */
    public static final String YM_GC = "gc";
    /**
     * 装修
     */
    public static final String YM_ZX = "zx";
    /**
     * 育儿
     */
    public static final String YM_YE = "ye";
    /**
     * 旅游
     */
    public static final String YM_LY = "ly";
    /**
     * 零钱
     */
    public static final String YM_LQ = "lq";
    //圆梦计划投标项目筛选
    /**
     * 筹款中
     */
    public static final String YM_NOW = "TZZ";
    /**
     * 待放款
     */
    public static final String YM_WAIT = "YMB";
    /**
     * 回款中
     */
    public static final String YM_BACK = "HKZ";
    /**
     * 已结束
     */
    public static final String YM_OVER = "YWC";
    //明细状态
    /**
     * 扣款
     */
    public static final String YM_REDECE = "扣款";
    /**
     * 回款
     */
    public static final String YM_ADD = "回款";
    // 计划加入状态
    /**
     * 未加入
     */
    public static final String YM_NOTJOIN = "notJoin";
    /**
     * 已加入
     */
    public static final String YM_OPEN = "open";
    /**
     * 已完成/终止
     */
    public static final String YM_CLOSE = "close";
     /* --------------------------------------*/
     /*------ 圆梦转入转出 -----------------------*/
    /**
     * 转入
     */
    public static final String YM_INPUT = "input";
    /**
     * 转出
     */
    public static final String YM_OUTPUT = "output";
     /* --------------------------------------*/
     /*------ 圆梦转入转出 -----------------------*/
    /**
     * 等本等息
     */
    public static final String REPAYMENT_TYPE_EQUAL = "等本等息";
    /**
     * 按月付息到期还款
     */
    public static final String REPAYMENT_TYPE_MONTH = "按月付息到期还款";
     /* --------------------------------------*/

     /*------ 消息订阅开关类型 -----------------------*/
    /**
     * 放款回款消息类型
     */
    public static final String PUSH_TYPE_INVEST = "invest";
    /**
     * 红包到期提醒类型
     */
    public static final String PUSH_TYPE_GIFT = "gift";
     /* --------------------------------------*/

     /*------ 推送跳转 -----------------------*/
    /**
     * 跳转activity key
     */
    public static final String UM_KEY_ACTIVITY = "um_acitivty";
    /**
     * 跳转web key
     */
    public static final String UM_KEY_WEB = "um_url";
    /**
     * 跳转web的title
     */
    public static final String UM_KEY_WEB_TITLE = "um_title";
    /**
     * APP首页
     */
    public static final String UM_HOME = "MainViewController";
    /**
     * 项目列表页
     */
    public static final String UM_PROJECT = "ItemListViewController";
    /**
     * 账户总览页
     */
    public static final String UM_MINE = "MineViewController";
    /**
     * 公告列表页
     */
    public static final String UM_NOTICE = "RecentNewsController";
    /**
     * 登录页
     */
    public static final String UM_LOGIN = "LoginViewController";
    /**
     * 注册页
     */
    public static final String UM_REGIST = "RegistViewController";
    /**
     * 圆梦账户页
     */
    public static final String UM_YM_ACCOUNT = "DreamerAccountInfoController";
    /**
     * 充值页
     */
    public static final String UM_RECHARGE = "RechargeViewController";
    /**
     * 意见反馈页
     */
    public static final String UM_FEED = "FeedbackViewController";
    /**
     * 我的投资页
     */
    public static final String UM_INVEST_LIST = "MineInvestManagerController";
    /**
     * 红包页
     */
    public static final String UM_GIFT_LIST = "RedPacketManagerVController";
    /**
     * 交易明细页
     */
    public static final String UM_DEAL_LIST = "ExchangeDetailController";
    /**
     * 我要借款页
     */
    public static final String UM_LEND = "WCJRSpecialLoadUrlController_borrow";
    /**
     * 复制app://web_copy{复制内容}
     */
    public static final String WEB_COPY_KEY = "web_copy";
     /* --------------------------------------*/
}
