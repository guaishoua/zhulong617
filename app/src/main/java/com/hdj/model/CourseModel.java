package com.hdj.model;

import android.content.Context;

import com.hdj.base.Application1907;
import com.hdj.frame.ApiConfig;
import com.hdj.frame.ApiService;
import com.hdj.frame.IContract;
import com.hdj.frame.NetManger;
import com.hdj.util.ParamHashMap;
import com.hdj.zhulong.R;

import java.util.Map;

public class CourseModel implements IContract.IModel {
    NetManger netManger = NetManger.getInstance();
    private Context mContext = Application1907.get07ApplicationContext();

    @Override
    public void getData(IContract.IPresenter presenter, int whichApi, Object[] params) {
        switch (whichApi) {
            case ApiConfig.GET_COURSE_DATA:
                Map<String, Object> map = (ParamHashMap) params[0];
                int loadType = (int) params[1];
                netManger.netWork(netManger.getService(mContext.getString(R.string.edu_openapi)).getCourseData(map),presenter,whichApi,loadType);
                break;
        }
    }
}
