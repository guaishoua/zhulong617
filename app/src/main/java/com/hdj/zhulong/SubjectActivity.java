package com.hdj.zhulong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hdj.adater.SubjectAdapter;
import com.hdj.base.BaseMvpActivity;
import com.hdj.constans.ConstantKey;
import com.hdj.data.BaseInfo;
import com.hdj.data.SpecialtyChooseEntity;
import com.hdj.frame.ApiConfig;
import com.hdj.model.MyModel;
import com.hdj.utils.newAdd.SharedPrefrenceUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hdj.contants.JumpConstant.*;

public class SubjectActivity extends BaseMvpActivity<MyModel> {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.title_content)
    TextView titleContent;
    private SubjectAdapter mAdapter;
    @BindView(R.id.more_content)
    TextView moreContent;
    private List<SpecialtyChooseEntity> mListData = new ArrayList<>();
    private String mFrom;

    @Override
    protected MyModel initModel() {
        return new MyModel();
    }

    @Override
    protected void initData() {
        if (SharedPrefrenceUtils.getSerializableList(this, ConstantKey.SUBJECT_LIST) != null) {
            mListData.addAll(SharedPrefrenceUtils.getSerializableList(this, ConstantKey.SUBJECT_LIST));
            mAdapter.notifyDataSetChanged();
        } else {
            myPresenter.getData(ApiConfig.SUBJECT);
        }

    }

    @Override
    protected void initView() {
        mFrom = getIntent().getStringExtra(JUMP_KEY);
        titleContent.setText(getString(R.string.select_subject));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SubjectAdapter(mListData, this);
        recyclerView.setAdapter(mAdapter);
        moreContent.setText("完成");
        moreContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mApplication.getSelectedInfo() == null){
                    showToast("请选择专业");
                    return;
                }
                if(mFrom.equals(SPLASH_TO_SUB)){
                    if(mApplication.isLogin()){
                        startActivity(new Intent(SubjectActivity.this,HomeActivity.class));
                    }else {
                        startActivity(new Intent(SubjectActivity.this,LoginActivity.class).putExtra(JUMP_KEY,SUB_TO_LOGIN));
                    }
                }
                finish();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_subject;
    }

    @Override
    protected void netSuccess(int whichApi, Object[] params) {
        switch (whichApi) {
            case ApiConfig.SUBJECT:
                BaseInfo<List<SpecialtyChooseEntity>> info = (BaseInfo<List<SpecialtyChooseEntity>>) params[0];
                mListData.addAll(info.result);
                mAdapter.notifyDataSetChanged();
                SharedPrefrenceUtils.putSerializableList(this, ConstantKey.SUBJECT_LIST, mListData);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPrefrenceUtils.putObject(this, ConstantKey.SUBJECT_SELECT, mApplication.getSelectedInfo());
    }

    @Override
    protected void netFailed(int whichApi, Throwable throwable) {

    }


}
