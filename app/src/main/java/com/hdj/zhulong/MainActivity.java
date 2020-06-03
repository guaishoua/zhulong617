package com.hdj.zhulong;


import android.content.Intent;

import com.hdj.base.BaseMvpActivity;
import com.hdj.model.MyModel;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseMvpActivity {

    int count = 2;

    @Override
    protected MyModel initModel() {
        return new MyModel();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count --;
                if(count == 0){
                    startActivity(new Intent(MainActivity.this,AdvertActivity.class));
                    finish();
                }
            }
        }, 0,1000);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void netSuccess(int whichApi, Object[] params) {

    }

    @Override
    protected void netFailed(int whichApi, Throwable throwable) {

    }
}
