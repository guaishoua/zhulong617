package com.hdj.zhulong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hdj.base.BaseMvpActivity;
import com.hdj.data.BaseInfo;
import com.hdj.frame.ApiConfig;
import com.hdj.interfaces.MyTextWatcher;
import com.hdj.model.AccountModel;
import com.hdj.utils.newAdd.RegexUtil;
import com.hdj.utils.newAdd.SoftInputControl;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import razerdp.design.DialogPopup;

import static com.hdj.contants.JumpConstant.JUMP_KEY;

public class RegisterActivity extends BaseMvpActivity<AccountModel> implements DialogPopup.DialogClickCallBack {


    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.area_code)
    TextView areaCode;
    @BindView(R.id.verify_name)
    EditText verifyName;
    @BindView(R.id.verify_code)
    EditText verifyCode;
    @BindView(R.id.get_verify_code)
    TextView getVerifyCode;
    @BindView(R.id.next_page)
    TextView nextPage;
    private Disposable mTimeObserver;
    private DialogPopup mConfirmDialog;
    @Override
    protected AccountModel initModel() {
        return new AccountModel();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        titleContent.setText("填写手机号码");
        nextPage.setEnabled(false);
        verifyCode.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onMyTextChanged(CharSequence s, int start, int before, int count) {
                nextPage.setEnabled(s.length() == 6 ? true : false);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void netSuccess(int whichApi, Object[] params) {
        switch (whichApi) {
            case ApiConfig.CHECK_PHONE_IS_USED:
                BaseInfo checkInfo = (BaseInfo) params[0];
                if (checkInfo.isSuccess()){
                    mConfirmDialog = new DialogPopup(this, true);
                    mConfirmDialog.setContent(verifyName.getText().toString()+"是否将短信发送到该手机");
                    mConfirmDialog.setDialogClickCallBack(this);
                    mConfirmDialog.showPopupWindow();
                } else {
                    showToast("该手机已注册");
                }
                break;
            case ApiConfig.SEND_REGISTER_VERIFY:
                BaseInfo sendResult = (BaseInfo) params[0];
                if (sendResult.isSuccess()){
                    showToast("验证码发送成功");
                    goTime();
                }else showLog(sendResult.msg);
                break;
            case ApiConfig.REGISTER_PHONE:
                BaseInfo info = (BaseInfo) params[0];
                if (info.isSuccess()){
                    showToast("验证码验证成功");
                    startActivity(new Intent(this,RegisterPhoneActivity.class).putExtra(JUMP_KEY,areaCode.getText().toString() + verifyName.getText().toString().trim()));
                    finish();
                } else showLog(info.msg);
                break;
        }
    }

    @Override
    protected void netFailed(int whichApi, Throwable throwable) {

    }



    @OnClick({R.id.iv_back,R.id.get_verify_code, R.id.next_page})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.get_verify_code:
                if(verifyName.getText() ==null){
                    showToast("请输入手机号");
                    return;
                }
                boolean isPhone = RegexUtil.isPhone(verifyName.getText().toString().trim());
                if(isPhone){
                    SoftInputControl.hideSoftInput(this,verifyName);
                    myPresenter.getData(ApiConfig.CHECK_PHONE_IS_USED,areaCode.getText().toString()+verifyName.getText().toString().trim());
                }
                break;
            case R.id.next_page:
                myPresenter.getData(ApiConfig.REGISTER_PHONE, areaCode.getText().toString() + verifyName.getText().toString().trim(), verifyCode.getText().toString().trim());
                break;
        }
    }

    private int preTime = 60;

    private void goTime() {
        mTimeObserver = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> {
                    if (preTime - l > 0) {
                        getVerifyCode.setText(preTime - l + "s");
                    } else {
                        getVerifyCode.setText("获取验证码");
                        disPose();
                    }
                });
    }

    private void disPose() {
        if (mTimeObserver != null && !mTimeObserver.isDisposed()) mTimeObserver.dispose();
    }

    @Override
    protected void onStop() {
        super.onStop();
        disPose();
    }

    @Override
    public void onClick(int type) {
        if (type == mConfirmDialog.OK){
            myPresenter.getData(ApiConfig.SEND_REGISTER_VERIFY,areaCode.getText().toString() + verifyName.getText().toString().trim());
        }
        mConfirmDialog.dismiss();
    }
}
