package com.hdj.zhulong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.hdj.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class AdvertActivity extends BaseActivity {

    private ImageView iv_advert;
    private Button click;
    int count = 5;
    private Timer timer;

    @Override
    protected void initData() {

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
    }

    @Override
    protected void initView() {
        iv_advert = (ImageView) findViewById(R.id.iv_advert);
        click = (Button) findViewById(R.id.click);
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
}
