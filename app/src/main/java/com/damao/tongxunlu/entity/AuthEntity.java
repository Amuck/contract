package com.damao.tongxunlu.entity;


import com.damao.tongxunlu.util.ConstanceUtil;

/**
 * Created by chenlong on 2017/8/7.
 *
 */

public class AuthEntity {
    private String osName;
    private String imei;
    private String osVersion;
    private String appVersion;
    private String timeStamp;
    private String uId;
    private String longitude;
    private String latitude;
    private String authCode;
    private String token;
    private String ip;

    private AuthEntity(Builder builder) {
        this.imei = builder.imei;
        this.osVersion = builder.osVersion;
        this.appVersion = builder.appVersion;
        this.timeStamp = builder.timeStamp;
        this.uId = builder.uId;
        this.longitude = builder.longitude;
        this.latitude = builder.latitude;
        this.authCode = builder.authCode;
        this.token = builder.token;
        this.ip = builder.ip;
        this.osName = ConstanceUtil.OS_TYPE;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getOsNanme() {
        return osName;
    }

    public void setOsNanme(String osNanme) {
        this.osName = osNanme;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserId() {
        return uId;
    }

    public void setUserId(String userId) {
        this.uId = userId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public static class Builder {
        private String imei;
        private String osVersion;
        private String appVersion;
        private String timeStamp;
        private String uId;
        private String longitude;
        private String latitude;
        private String authCode;
        private String token;
        private String ip;

        public Builder(String appVersion, String timeStamp, String authCode) {
            this.appVersion = appVersion;
            this.timeStamp = timeStamp;
            this.authCode = authCode;
        }

        public Builder imei(String imei) {
            this.imei = imei;
            return this;
        }

        public Builder osVersion(String osVersion) {
            this.osVersion = osVersion;
            return this;
        }

        public Builder userId(String userId) {
            this.uId = userId;
            return this;
        }

        public Builder longitude(String longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder latitude(String latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public AuthEntity build() {
            return new AuthEntity(this);
        }
    }
}
