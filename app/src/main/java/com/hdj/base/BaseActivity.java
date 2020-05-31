package com.hdj.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hdj.zhulong.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayout();

}
