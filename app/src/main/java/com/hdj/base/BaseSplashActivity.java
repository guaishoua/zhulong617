package com.hdj.base;

import com.hdj.data.Device;
import com.hdj.frame.FrameApplication;
import com.hdj.secret.SystemUtils;
import com.hdj.utils.NetworkUtils;
import com.hdj.zhulong.R;

public abstract class BaseSplashActivity extends BaseMvpActivity {


    @Override
    protected int getLayout() {
        return R.layout.activity_advert;
    }

    public void initDevice() {
        Device device = new Device();
        device.setScreenWidth(SystemUtils.getSize(this).x);
        device.setScreenHeight(SystemUtils.getSize(this).y);
        device.setDeviceName(SystemUtils.getDeviceName());
        device.setSystem(SystemUtils.getSystem(this));
        device.setVersion(SystemUtils.getVersion(this));
        device.setDeviceId(SystemUtils.getDeviceId(this));
        device.setLocalIp(NetworkUtils.getLocalIpAddress());
        FrameApplication.getFrameApplication().setDeviceInfo(device);
    }
}
