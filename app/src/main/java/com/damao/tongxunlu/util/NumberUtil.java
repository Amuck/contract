package com.damao.tongxunlu.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by chenlong on 2017/9/1.
 * 处理数字
 */

public class NumberUtil {
    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }
    /**
     *
     * 提供精确的小数位四舍五入处理。
     *
     * @param v
     *            需要四舍五入的数字
     *
     * @param scale
     *            小数点后保留几位
     *
     * @return 四舍五入后的结果
     *
     */

    public static double roundForNumber(double v,int scale){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取以万为单位的金额,保留两位小数
     * @param nstr
     * @return
     */
    public static String getNumForWan(String nstr) {
        String str;
        if(isNumber(nstr)){
            double v = Double.parseDouble(nstr);
            v = v / 10000;
//            double vals = roundForNumber(v,2);
            str = String.valueOf(v);
            BigDecimal db = new BigDecimal(str);
            str = db.toPlainString();
        }else{
            str = nstr;
        }
        if (str.endsWith(".00")){
            str = str.replace(".00", "");
        }
        if (str.endsWith(".0")){
            str = str.replace(".0", "");
        }
        return str;
    }

    /**
     * 每三位逗号分隔
     * @param data
     * @return
     */
    public static String formatString(float data) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(data);
    }

    /**
     * 每三位逗号分隔
     * @param data
     * @return
     */
    public static String formatString(double data) {
        DecimalFormat df = new DecimalFormat(",##0.00");
        return df.format(data);
    }

    /**
     * 每三位逗号分隔
     * @param money
     */
    public static String getLastMoney(String money) {
        if (TextUtils.isEmpty(money))
            return "0";
        if (money.contains(","))
            return money;
        if (NumberUtil.isNumber(money)) {
            double v = Double.parseDouble(money);
            if (v == 0)
                return "0.00";
            return NumberUtil.formatString(v);
        }
        return "0";
    }

     /**
      * 去掉12.0之后的.0
     * @param s
     * @return
     */
    public static String removeZero(String s) {
        if (TextUtils.isEmpty(s))
            return "0";
        if (!s.contains("."))
            return s;
        String[] ss = s.split("\\.");
        if ("0".equals(ss[1]) || "00".equals(ss[1])) {
            return ss[0];
        } else if (ss[1].endsWith("0")) {
            return s.substring(0, s.length() - 1);
        }
        else {
            return s;
        }
    }

    /**
     * 是否org比target大
     * @param target
     * @param org
     * @return  true：org大
     */
    public static boolean isBiggerThan(int target, Object org) {
        if (!isInteger(org.toString()))
            return false;
        int orgInt = Integer.valueOf(org.toString());
        return orgInt > target;
    }
}
