package com.damao.tongxunlu.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chenlong on 2017/8/7.
 *
 */

public class BaseEntity {
    public static final String ENTITY_RESULT_SUCCESS = "200";
    @SerializedName("errorCode")
    @Expose
    private String errorCode;

    @SerializedName("msg")
    @Expose
    protected String msg;

    public String getCode() {
        return errorCode;
    }

    public void setCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
