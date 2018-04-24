package com.damao.tongxunlu.entity;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by chenlong on 2018/1/12.
 * 通话记录
 */

public class CallEntity extends BaseEntity implements Comparable<CallEntity>, Serializable {
    /**
     * 通话类型：接入INCOMING_TYPE = 1
     * 打出OUTGOING_TYPE = 2
     * 未接MISSED_TYPE = 3
     * 语音邮件VOICEMAIL_TYPE = 4
     * 拒接REJECTED_TYPE = 5;
     * 黑名单BLOCKED_TYPE = 6
     * 呼叫转移ANSWERED_EXTERNALLY_TYPE = 7
     */
    private int type;
    /**
     * 电话号码
     */
    private String number;
    /**
     * 城市代码
     */
    private String countryiso;
    /**
     * 通话日期
     */
    private String dates;
    /**
     * 通话时间（秒）
     */
    private String  duration;
    /**
     * 是否被告知
     */
    private boolean isNew;
    /**
     * 缓存名字，可能为空
     */
    private String remarkName;
    /**
     * 缓存名字（Home，Work），可能为空
     */
    private int numbertype;
    /**
     * 缓存电话标签，可能为空
     */
    private String numberlabel;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 是否已读
     */
    private boolean  is_read;
    /**
     * 位置
     */
    private String location;
    /**
     * 缓存照片，可能为空
     */
    private long photo_id;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCountryiso() {
        return countryiso;
    }

    public void setCountryiso(String countryiso) {
        this.countryiso = countryiso;
    }


    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public int getNumbertype() {
        return numbertype;
    }

    public void setNumbertype(int numbertype) {
        this.numbertype = numbertype;
    }

    public String getNumberlabel() {
        return numberlabel;
    }

    public void setNumberlabel(String numberlabel) {
        this.numberlabel = numberlabel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(long photo_id) {
        this.photo_id = photo_id;
    }

    @Override
    public int compareTo(@NonNull CallEntity o) {
        return o.getDates().compareTo(this.dates);
    }
}
