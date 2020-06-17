package com.hdj.zhulong;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.hdj.base.BaseMvpActivity;
import com.hdj.constans.ConstantKey;
import com.hdj.data.BaseInfo;
import com.hdj.data.LoginInfo;
import com.hdj.data.PersonHeader;
import com.hdj.data.ThirdLoginData;
import com.hdj.frame.ApiConfig;
import com.hdj.model.AccountModel;
import com.hdj.utils.newAdd.SharedPrefrenceUtils;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhulong.eduvideo.wxapi.WXEntryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.hdj.contants.JumpConstant.JUMP_KEY;
import static com.hdj.contants.JumpConstant.REGISTER_TO_LOGIN;
import static com.hdj.contants.JumpConstant.SPLASH_TO_LOGIN;
import static com.hdj.contants.JumpConstant.SUB_TO_LOGIN;

public class LoginActivity extends BaseMvpActivity<AccountModel> implements LoginView.LoginViewCallBack {

    @BindView(R.id.login_view)
    LoginView mLoginView;
    private Disposable mSubscribe;
    private String phoneNum;

    private String mFromType;
    private ThirdLoginData mThirdData;

    @Override
    protected AccountModel initModel() {
        return new AccountModel();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mFromType = getIntent().getStringExtra(JUMP_KEY);
        mLoginView.setLoginViewCallBack(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    private void jump() {
        if (mFromType.equals(SPLASH_TO_LOGIN) || mFromType.equals(SUB_TO_LOGIN))//从 广告 或 选择专业页面跳过来
            startActivity(new Intent(this, HomeActivity.class));
        this.finish();
    }

    private long time = 60l;

    private void goTime() {
        mSubscribe = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(goTime -> {
                    mLoginView.getVerifyCode.setText(time - goTime + "s");
                    if (time - goTime < 1) doPre();
                });
    }

    @OnClick({R.id.close_login, R.id.register_press, R.id.forgot_pwd, R.id.login_by_qq, R.id.login_by_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close_login:
                if (!TextUtils.isEmpty(mFromType) && (mFromType.equals(SUB_TO_LOGIN) || mFromType.equals(SPLASH_TO_LOGIN) || mFromType.equals(REGISTER_TO_LOGIN))) {
                    startActivity(new Intent(this, HomeActivity.class));
                }
                finish();
                break;
            case R.id.register_press:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.forgot_pwd:
                break;
            case R.id.login_by_qq:
                break;
            case R.id.login_by_wx:
                doWechatLogin();
                break;
        }
    }

    private void doWechatLogin() {
        WXEntryActivity.setOnWeChatLoginResultListener(it -> {
            int errorCode = it.getIntExtra("errorCode", 0);
            String normalCode = it.getStringExtra("normalCode");
            switch (errorCode) {
                case 0:
                    showLog("用户已同意微信登录");
                    myPresenter.getData(ApiConfig.GET_WE_CHAT_TOKEN, normalCode);
                    break;
                case -4:
                    showToast("用户拒绝授权");
                    break;
                case -2:
                    showToast("用户取消");
                    break;

            }
        });
        IWXAPI weChatApi = WXAPIFactory.createWXAPI(this, null);
        weChatApi.registerApp(ConstantKey.WX_APP_ID);
        if (weChatApi.isWXAppInstalled()) {
            doWeChatLogin();
        } else showToast("请先安装微信");
    }

    private void doWeChatLogin() {
        SendAuth.Req request = new SendAuth.Req();
//        snsapi_base 和snsapi_userinfo  静态获取和同意后获取
        request.scope = "snsapi_userinfo";
        request.state = "com.zhulong.eduvideo";
        IWXAPI weChatApi = WXAPIFactory.createWXAPI(this, ConstantKey.WX_APP_ID);
        weChatApi.sendReq(request);
    }

    private void doPre() {
        if (mSubscribe != null && !mSubscribe.isDisposed()) mSubscribe.dispose();
        mLoginView.getVerifyCode.setText("获取验证码");
    }

    @Override
    protected void netSuccess(int whichApi, Object[] params) {
        switch (whichApi) {
            case ApiConfig.SEND_VERIFY:
                BaseInfo<String> info = (BaseInfo<String>) params[0];
                String result = info.result != null && !TextUtils.isEmpty(info.result) ? info.result : info.msg;
                if (result == null && TextUtils.isEmpty(result)) {
                    result = "已发送验证";
                    showToast(result);
                    goTime();
                } else {
                    showToast(result);
                }

                break;
            case ApiConfig.ACCOUNT_LOGIN:       //账号 验证码 登录
            case ApiConfig.VERIFY_LOGIN:
            case ApiConfig.POST_WE_CHAT_LOGIN_INFO:
                BaseInfo<LoginInfo> baseInfo = (BaseInfo<LoginInfo>) params[0];
                if (baseInfo.isSuccess()) {
                    LoginInfo loginInfo = baseInfo.result;
                    if (!TextUtils.isEmpty(phoneNum)) loginInfo.login_name = phoneNum;
                    mApplication.setLoginInfo(loginInfo);
                    myPresenter.getData(ApiConfig.GET_HEADER_INFO);
                } else if (baseInfo.errNo == 1300) {
                    Intent intent = new Intent(this, ThirdAccoutBindActivity.class);
                    startActivityForResult(intent.putExtra("thirdData", mThirdData), ConstantKey.LOGIN_TO_BIND);
                } else {
                    showToast(baseInfo.msg);
                }
                break;
            case ApiConfig.GET_HEADER_INFO:
                PersonHeader personHeader = ((BaseInfo<PersonHeader>) params[0]).result;
                mApplication.getLoginInfo().personHeader = personHeader;
                SharedPrefrenceUtils.putObject(this, ConstantKey.LOGIN_INFO, mApplication.getLoginInfo());
                jump();
                break;
            case ApiConfig.GET_WE_CHAT_TOKEN:
                JSONObject allJson = null;
                try {
                    allJson = new JSONObject(params[0].toString());
                } catch (JSONException pE) {
                    pE.printStackTrace();
                }
                mThirdData = new ThirdLoginData(3);
                mThirdData.setOpenid(allJson.optString("openid"));
                mThirdData.token = allJson.optString("access_token");
                mThirdData.refreshToken = allJson.optString("refresh_token");
                mThirdData.utime = allJson.optLong("expires_in") * 1000;
                mThirdData.unionid = allJson.optString("unionid");
                myPresenter.getData(ApiConfig.POST_WE_CHAT_LOGIN_INFO, mThirdData);
                break;
        }
    }

    @Override
    protected void netFailed(int whichApi, Throwable throwable) {

    }


    @Override
    public void sendVerifyCode(String phoneNum) {
        this.phoneNum = phoneNum;
        myPresenter.getData(ApiConfig.SEND_VERIFY, phoneNum);
    }

    @Override
    public void loginPress(int type, String userName, String pwd) {
        doPre();
        if (mLoginView.mCurrentLoginType == mLoginView.VERIFY_TYPE)
            myPresenter.getData(ApiConfig.VERIFY_LOGIN, userName, pwd);
        else myPresenter.getData(ApiConfig.ACCOUNT_LOGIN, userName, pwd);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doPre();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConstantKey.BIND_BACK_LOGIN){
            myPresenter.getData(ApiConfig.POST_WE_CHAT_LOGIN_INFO, mThirdData);
        }
    }
}
