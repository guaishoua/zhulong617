package com.hdj.zhulong;

import android.content.Intent;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hdj.base.BaseSplashActivity;
import com.hdj.constans.ConstantKey;
import com.hdj.data.AdvertBean;
import com.hdj.data.SpecialtyChooseEntity;
import com.hdj.frame.ApiConfig;
import com.hdj.model.MyModel;
import com.hdj.secret.SystemUtils;
import com.hdj.utils.newAdd.SharedPrefrenceUtils;

import java.util.Timer;
import java.util.TimerTask;

public class AdvertActivity extends BaseSplashActivity {

    private ImageView iv_advert;
    private Button click;
    int count = 5;
    private Timer timer;
    private SpecialtyChooseEntity.DataBean mSelectedInfo;

    @Override
    protected MyModel initModel() {
        return new MyModel();
    }

    @Override
    protected void initData() {
        mSelectedInfo = SharedPrefrenceUtils.getObject(this, ConstantKey.SUBJECT_SELECT);
        String specialtyId = "";
        if (mSelectedInfo != null && !TextUtils.isEmpty(mSelectedInfo.getSpecialty_id())) {
            mApplication.setSelectedInfo(mSelectedInfo);
            specialtyId = mSelectedInfo.getSpecialty_id();
        }
        Point realSize = SystemUtils.getRealSize(this);
        myPresenter.getData(ApiConfig.TEST_POST,"APP_QD_01","0");

    }

    @Override
    protected void initView() {
        iv_advert = (ImageView) findViewById(R.id.iv_advert);
        click = (Button) findViewById(R.id.click);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initDevice();
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
                    jump();
                }
            }
        }, 0, 1000);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdvertActivity.this,HomeActivity.class));
                timer.cancel();
                jump();
            }
        });
    }



    private void jump() {
        startActivity(new Intent(this,mSelectedInfo != null && !TextUtils.isEmpty(mSelectedInfo.getSpecialty_id()) ? mApplication.isLogin() ? HomeActivity.class : LoginActivity.class : SubjectActivity.class ));
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
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
