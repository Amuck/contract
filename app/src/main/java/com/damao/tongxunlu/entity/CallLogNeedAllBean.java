package com.damao.tongxunlu.entity;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by chenlong on 2018/2/1.
 *
 */

public class CallLogNeedAllBean implements Serializable, Comparable<CallLogNeedAllBean> {
    /**
     * 电话号码
     */
    private String number;
    /**
     * 备注名称
     */
    private String remarkName;
    /**
     * 通话次数
     */
    private int count;
    /**
     * 通话类型 0,未接 1.拨出 2,拨入
     */
    private int type;
    /**
     * 归属地
     */
    private String location;
    /**
     * 最后通话日期
     */
    private String lastDate;
    /**
     * 最后通话日期的Long值
     */
    private Long lastDateLong;

    private ArrayList<CallEntity> callEntities = new ArrayList<>();

    public ArrayList<CallEntity> getCallEntities() {
        return callEntities;
    }

    public void setCallEntities(ArrayList<CallEntity> callEntities) {
        this.callEntities = callEntities;
    }

    public void setLastDateLong(Long lastDateLong) {
        this.lastDateLong = lastDateLong;
    }

    public Long getLastDateLong() {
        return lastDateLong;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "CallLogNeedAllBean{" +
                "number='" + number + '\'' +
                ", remarkName='" + remarkName + '\'' +
                ", count=" + count +
                ", type=" + type +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NonNull CallLogNeedAllBean o) {
        return o.getLastDate().compareTo(lastDate);
    }
}
