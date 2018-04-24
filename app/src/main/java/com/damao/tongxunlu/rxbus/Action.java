package com.damao.tongxunlu.rxbus;

/**
 * Created by chenlong on 2017/3/14.
 * rxbus 封装对象
 */

public class Action {
    public int code;
    public Object data;

    Action(int code, Object data) {
        this.code = code;
        this.data = data;
    }
}
