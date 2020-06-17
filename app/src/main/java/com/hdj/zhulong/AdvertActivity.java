package com.hdj.zhulong;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdj.base.Application1907;
import com.hdj.base.BaseSplashActivity;
import com.hdj.constans.ConstantKey;
import com.hdj.data.AdvertBean;
import com.hdj.data.BaseInfo;
import com.hdj.data.LoginInfo;
import com.hdj.data.MainAdEntity;
import com.hdj.data.SpecialtyChooseEntity;
import com.hdj.frame.ApiConfig;
import com.hdj.model.MyModel;
import com.hdj.secret.SystemUtils;
import com.hdj.utils.newAdd.GlideUtil;
import com.hdj.utils.newAdd.SharedPrefrenceUtils;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

import static com.hdj.contants.JumpConstant.*;

public class AdvertActivity extends BaseSplashActivity {

    private ImageView iv_advert;
    private TextView time_view;
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
        myPresenter.getData(ApiConfig.TEST_POST, specialtyId, realSize.x, realSize.y);
        LoginInfo object = SharedPrefrenceUtils.getObject(this, ConstantKey.LOGIN_INFO);
        if (object != null) Application1907.getFrameApplication().setLoginInfo(object);
    }


    @Override
    protected void initView() {
        iv_advert = (ImageView) findViewById(R.id.iv_advert);
        time_view = (TextView) findViewById(R.id.time_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initDevice();
        time_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdvertActivity.this, HomeActivity.class));
                timer.cancel();
                jump();
            }
        });
    }

    private void goTime() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        count--;
                        time_view.setText("跳过 " + count);
                    }
                });
                if (count == 1) {
                    jump();
                }
            }
        }, 0, 1000);
    }

    private void jump() {
        Observable.just("我是防抖动").debounce(20, TimeUnit.MILLISECONDS).subscribe(p -> {
            if (mSelectedInfo != null && !TextUtils.isEmpty(mSelectedInfo.getSpecialty_id())) { //选择的专业不是空，就是已经选择了专业了
                if (mApplication.isLogin()) {        //登录了跳转 主页
                    startActivity(new Intent(this, HomeActivity.class));
                } else { //没登录 走登录页面
                    startActivity(new Intent(this, LoginActivity.class).putExtra(JUMP_KEY, SPLASH_TO_LOGIN));
                }
            } else {
                startActivity(new Intent(this, SubjectActivity.class).putExtra(JUMP_KEY, SPLASH_TO_SUB));
            }
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private static final String TAG = "AdvertActivity";

    @Override
    protected void netSuccess(int whichApi, Object[] params) {
        switch (whichApi) {
            case ApiConfig.TEST_POST:
                AdvertBean advertBean = (AdvertBean) params[0];
                String jump_url = advertBean.getResult().getInfo_url();
                Log.d(TAG, "netSuccess: " + jump_url);
                GlideUtil.loadImage(iv_advert, jump_url);
                time_view.setVisibility(View.VISIBLE);
                goTime();
                break;
        }
    }

    @Override
    protected void netFailed(int whichApi, Throwable throwable) {

    }
}
