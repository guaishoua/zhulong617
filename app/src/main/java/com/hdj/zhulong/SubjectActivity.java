package com.hdj.zhulong;

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

public class SubjectActivity extends BaseMvpActivity<MyModel> {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.title_content)
    TextView titleContent;
    private SubjectAdapter mAdapter;

    private List<SpecialtyChooseEntity> mListData = new ArrayList<>();

    @Override
    protected MyModel initModel() {
        return new MyModel();
    }

    @Override
    protected void initData() {
        if (SharedPrefrenceUtils.getSerializableList(this, ConstantKey.SUBJECT_LIST) != null) {
            mListData.addAll( SharedPrefrenceUtils.getSerializableList(this, ConstantKey.SUBJECT_LIST));
            mAdapter.notifyDataSetChanged();
        } else{
            myPresenter.getData(ApiConfig.SUBJECT);
        }

    }

    @Override
    protected void initView() {
        titleContent.setText(getString(R.string.select_subject));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SubjectAdapter(mListData, this);
        recyclerView.setAdapter(mAdapter);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public SubjectActivity() {
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
                SharedPrefrenceUtils.putSerializableList(this,ConstantKey.SUBJECT_LIST,mListData);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPrefrenceUtils.putObject(this,ConstantKey.SUBJECT_SELECT,mApplication.getSelectedInfo());
    }

    @Override
    protected void netFailed(int whichApi, Throwable throwable) {

    }


}
