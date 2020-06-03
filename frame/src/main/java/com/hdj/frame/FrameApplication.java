package com.hdj.frame;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.hdj.data.Device;
import com.hdj.data.LoginInfo;
import com.hdj.data.SpecialtyChooseEntity;
import com.hdj.utils.UtilsApplication;


public class FrameApplication extends UtilsApplication {
    private static FrameApplication application;
    private Device mDeviceInfo;
    private LoginInfo mLoginInfo;
    private String cookie;
    private SpecialtyChooseEntity.DataBean selectedInfo;

    public SpecialtyChooseEntity.DataBean getSelectedInfo() {
        return selectedInfo;
    }

    public void setSelectedInfo(SpecialtyChooseEntity.DataBean pSelectedInfo) {
        selectedInfo = pSelectedInfo;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public boolean isLogin() {
        return mLoginInfo != null && !TextUtils.isEmpty(mLoginInfo.getUid());
    }

    public LoginInfo getLoginInfo() {
        return mLoginInfo;
    }

    public void setLoginInfo(LoginInfo mLoginInfo) {
        this.mLoginInfo = mLoginInfo;
    }

    public Device getDeviceInfo() {
        return mDeviceInfo;
    }

    public void setDeviceInfo(Device mDeviceInfo) {
        this.mDeviceInfo = mDeviceInfo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static FrameApplication getFrameApplication() {
        return application;
    }

    public static Context getFrameApplicationContext() {
        return application.getApplicationContext();
    }
}
