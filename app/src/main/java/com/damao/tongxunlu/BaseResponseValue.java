package com.damao.tongxunlu;

/**
 * Created by chenlong on 2017/8/10.
 *
 */

public class BaseResponseValue {
    protected String code;
    protected String msg;

    public BaseResponseValue() {
    }

    public BaseResponseValue(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
