package com.hdj.base;

import android.app.Application;
import android.content.Context;

import com.hdj.frame.FrameApplication;



public class Application1907 extends Application {
    private static Application1907 mApplication1907;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication1907 = this;
    }

    public Application1907 getApplication(){
        return mApplication1907;
    }

    public static Context get07ApplicationContext(){
        return mApplication1907.getApplicationContext();
    }
}
