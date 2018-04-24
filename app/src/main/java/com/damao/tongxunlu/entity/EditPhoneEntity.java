package com.damao.tongxunlu.entity;

/**
 * Created by chenlong on 2018/3/23.
 * 用来编辑
 */

public class EditPhoneEntity extends BaseEntity {
    private String phone;
    private String phone_id = null;
    private boolean isDelete = false;

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_id() {
        return phone_id;
    }

    public void setPhone_id(String phone_id) {
        this.phone_id = phone_id;
    }
}
