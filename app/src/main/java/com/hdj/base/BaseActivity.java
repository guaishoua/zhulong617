package com.hdj.base;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hdj.zhulong.R;

public class BaseActivity extends AppCompatActivity {
    public Application1907 mApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (Application1907) getApplication();
    }
}
