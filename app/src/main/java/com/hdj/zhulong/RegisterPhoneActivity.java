package com.hdj.zhulong;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.hdj.base.BaseMvpActivity;
import com.hdj.data.BaseInfo;
import com.hdj.frame.ApiConfig;
import com.hdj.interfaces.MyTextWatcher;
import com.hdj.model.AccountModel;
import com.hdj.zhulong.utils.CheckUserNameAndPwd;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hdj.contants.JumpConstant.*;

public class RegisterPhoneActivity extends BaseMvpActivity<AccountModel> {


    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.more_content)
    TextView moreContent;
    @BindView(R.id.accountContent)
    EditText accountContent;
    @BindView(R.id.cutLine)
    View cutLine;
    @BindView(R.id.accountSecret)
    EditText accountSecret;
    @BindView(R.id.editArea)
    ConstraintLayout editArea;
    @BindView(R.id.clearAccount)
    ImageView clearAccount;
    @BindView(R.id.visibleImage)
    ImageView visibleImage;
    @BindView(R.id.next_page)
    TextView nextPage;
    private String mPhoneNum;
    @Override
    protected AccountModel initModel() {
        return new AccountModel();
    }

    @Override
    protected void initData() {
        mPhoneNum = getIntent().getStringExtra("phoneNum");
    }

    @Override
    protected void initView() {
        titleContent.setText("创建账号");
        accountContent.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onMyTextChanged(CharSequence s, int start, int before, int count) {
                clearAccount.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                nextPage.setSelected(s.length() > 0 && accountSecret.getText().length() > 0);
            }
        });
        accountSecret.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onMyTextChanged(CharSequence s, int start, int before, int count) {
                visibleImage.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                nextPage.setSelected(s.length() > 0 && accountContent.getText().length() > 0);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register_phone;
    }

    @Override
    protected void netSuccess(int whichApi, Object[] params) {
        switch (whichApi) {
            case ApiConfig.NET_CHECK_USERNAME:
                BaseInfo baseInfo = (BaseInfo) params[0];
                if (baseInfo.isSuccess()) {
                    myPresenter.getData(ApiConfig.COMPLETE_REGISTER_WITH_SUBJECT, accountContent.getText().toString(), accountSecret.getText().toString(), mPhoneNum);
                } else if (baseInfo.errNo == 114) {
                    showToast("该用户名不可用");
                } else showToast(baseInfo.msg);
                break;
            case ApiConfig.COMPLETE_REGISTER_WITH_SUBJECT:
                BaseInfo base = (BaseInfo) params[0];
                if (base.errNo == 24 || base.errNo == 114 || base.isSuccess()) {
                    showToast("注册成功");
                    startActivity(new Intent(this, LoginActivity.class).putExtra(JUMP_KEY, REGISTER_TO_LOGIN));
                    finish();
                } else showToast(base.msg);
                break;
        }
    }

    @Override
    protected void netFailed(int whichApi, Throwable throwable) {

    }


    @OnClick({R.id.iv_back, R.id.clearAccount, R.id.visibleImage, R.id.next_page})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.clearAccount:
                accountContent.setText("");
                break;
            case R.id.visibleImage:
                accountSecret.setTransformationMethod(visibleImage.isSelected() ? PasswordTransformationMethod.getInstance() : HideReturnsTransformationMethod.getInstance());
                visibleImage.setSelected(!visibleImage.isSelected());
                break;
            case R.id.next_page:
                if (CheckUserNameAndPwd.verificationUsername(this, accountContent.getText().toString(), accountSecret.getText().toString()))
                    myPresenter.getData(ApiConfig.NET_CHECK_USERNAME, accountContent.getText().toString());
                break;
        }
    }


}
