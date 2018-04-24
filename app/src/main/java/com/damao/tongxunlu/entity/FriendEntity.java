package com.damao.tongxunlu.entity;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * 联系人
 */

public class FriendEntity extends BaseEntity implements Serializable{
    private String remark;
    private String account;
    private String nickName;
    private ArrayList<String> phoneNumbers = new ArrayList<>();
    private String area;
    private String headerUrl;
    private String pinyin;
    private String email;
    private byte[] dataHeaderUrl;
    /**
     * 是否是存储在sim中， 默认为false
     */
    private boolean isSim = false;

    public byte[] getDataHeaderUrl() {
        return dataHeaderUrl;
    }

    public void setDataHeaderUrl(byte[] dataHeaderUrl) {
        this.dataHeaderUrl = dataHeaderUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSim() {
        return isSim;
    }

    public void setSim(boolean sim) {
        isSim = sim;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public ArrayList<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public String getFirstPinyin(){
        return pinyin!=null?pinyin.substring(0,1):"";
    }
}
