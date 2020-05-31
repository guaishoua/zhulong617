package com.hdj.zhulong;


import android.content.Intent;

import com.hdj.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {

    int count = 2;

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
}
