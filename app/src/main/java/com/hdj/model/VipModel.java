package com.hdj.model;

import android.content.Context;

import com.hdj.base.Application1907;
import com.hdj.frame.ApiConfig;
import com.hdj.frame.IContract;
import com.hdj.frame.NetManger;
import com.hdj.util.ParamHashMap;
import com.hdj.zhulong.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VipModel implements IContract.IModel {
    NetManger netManger=NetManger.getInstance();
    private Context mContext = Application1907.get07ApplicationContext();
    @Override
    public void getData(IContract.IPresenter presenter, int whichApi, Object[] params) {
        switch (whichApi) {
            case ApiConfig.GET_VIP_LIST_DATA:
                ParamHashMap paramHashMap = (ParamHashMap) params[0];
                int loadType = (int) params[1];
                netManger.netWork(netManger.getService(mContext.getString(R.string.edu_openapi)).getVipListData(paramHashMap),presenter,whichApi,loadType);
                break;
            case ApiConfig.GET_VIP_LIVE_DATA:
                ParamHashMap paramHashMap2 = (ParamHashMap) params[0];
                OkHttpClient okHttpClient1 = new OkHttpClient();
                FormBody build2 = new FormBody.Builder()
                        .add("pro", (String) paramHashMap2.get("pro"))
                        .add("uid", (String) paramHashMap2.get("uid"))
                        .build();
                Request build3 = new Request.Builder()
                        .url("https://edu.zhulong.com/openapi/lesson/get_new_vip")
                        .post(build2)
                        .build();
                okHttpClient1.newCall(build3).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        presenter.onSuccess(whichApi, string);
                    }
                });
                break;
        }
    }
}
