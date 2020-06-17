package com.hdj.zhulong;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdj.base.BaseMvpActivity;
import com.hdj.constans.ConstantKey;
import com.hdj.data.ThirdLoginData;
import com.hdj.frame.ApiConfig;
import com.hdj.model.AccountModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThirdAccoutBindActivity extends BaseMvpActivity<AccountModel> {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.more_content)
    TextView moreContent;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.button_bind)
    Button buttonBind;
    private ThirdLoginData mThirdData;
    @Override
    protected AccountModel initModel() {
        return new AccountModel();
    }

    @Override
    protected void initData() {
        titleContent.setText("绑定账号");
    }

    @Override
    protected void initView() {
        mThirdData = getIntent().getParcelableExtra("thirdData");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_third_accout_bind;
    }

    @Override
    protected void netSuccess(int whichApi, Object[] params) {
        switch (whichApi) {
            case ApiConfig.BIND_ACCOUNT:
                setResult(ConstantKey.BIND_BACK_LOGIN);
                finish();
                break;
        }
    }

    @Override
    protected void netFailed(int whichApi, Throwable throwable) {

    }


    @OnClick({R.id.iv_back, R.id.button_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.button_bind:
                if (TextUtils.isEmpty(account.getText().toString())) {
                    showToast("用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    showToast("密码不能为空");
                    return;
                }
                myPresenter.getData(ApiConfig.BIND_ACCOUNT, account.getText().toString(), password.getText().toString(), mThirdData);
                break;
        }
    }
}
