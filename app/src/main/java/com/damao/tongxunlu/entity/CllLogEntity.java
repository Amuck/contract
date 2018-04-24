package com.damao.tongxunlu.entity;

import java.util.ArrayList;

/**
 * Created by chenlong on 2018/3/7.
 *
 */

public class CllLogEntity extends BaseEntity{
    private ArrayList<CallLogNeedAllBean> callLogs = new ArrayList<>();

    public ArrayList<CallLogNeedAllBean> getCallLogs() {
        return callLogs;
    }

    public void setCallLogs(ArrayList<CallLogNeedAllBean> callLogs) {
        this.callLogs = callLogs;
    }
}
