package com.hdj.zhulong;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.hdj.base.BaseActivity;
import com.hdj.base.BaseMvpActivity;
import com.hdj.data.AdvertBean;
import com.hdj.frame.ApiConfig;
import com.hdj.frame.MyModel;

import java.util.Timer;
import java.util.TimerTask;

public class AdvertActivity extends BaseMvpActivity {

    private ImageView iv_advert;
    private Button click;
    int count = 5;
    private Timer timer;

    @Override
    protected MyModel initModel() {
        return new MyModel();
    }

    @Override
    protected void initData() {
        myPresenter.getData(ApiConfig.TEST_POST,"APP_QD_01","0");

    }

    @Override
    protected void initView() {
        iv_advert = (ImageView) findViewById(R.id.iv_advert);
        click = (Button) findViewById(R.id.click);

        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        count--;
                        click.setText("跳过 " + count);
                    }
                });

                if (count == 1) {
                    startActivity(new Intent(AdvertActivity.this,HomeActivity.class));
                    finish();
                }
            }
        }, 0, 1000);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdvertActivity.this,HomeActivity.class));
                timer.cancel();
                finish();
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_advert;
    }

    private static final String TAG = "AdvertActivity";
    @Override
    protected void netSuccess(int whichApi, Object[] params) {
        switch (whichApi){
            case ApiConfig.TEST_POST:
                AdvertBean advertBean = (AdvertBean) params[0];
                String jump_url = advertBean.getResult().getInfo_url();
                Log.d(TAG, "netSuccess: "+jump_url);
                Glide.with(AdvertActivity.this).load(jump_url).into(iv_advert);
                break;
        }
    }

    @Override
    protected void netFailed(int whichApi, Throwable throwable) {

    }
}
