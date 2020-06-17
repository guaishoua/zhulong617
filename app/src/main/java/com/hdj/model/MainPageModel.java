package com.hdj.model;

import android.content.Context;

import com.hdj.base.Application1907;
import com.hdj.frame.ApiConfig;
import com.hdj.frame.ApiService;
import com.hdj.frame.IContract;
import com.hdj.frame.NetManger;
import com.hdj.util.ParamHashMap;
import com.hdj.zhulong.R;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainPageModel implements IContract.IModel {
    NetManger netManger = NetManger.getInstance();
    private Context mContext = Application1907.get07ApplicationContext();

    @Override
    public void getData(IContract.IPresenter presenter, int whichApi, Object[] params) {
        switch (whichApi) {
            case ApiConfig.GET_HOME_LIVE_DATA:
                Map<String, Object> map = (ParamHashMap) params[0];
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody build = new FormBody.Builder()
                        .add("pro", (String) map.get("pro"))
                        .add("more_live", "1")
                        .add("is_new", "1")
                        .add("new_banner", "1")
                        .build();
                Request build1 = new Request.Builder()
                        .url("https://edu.zhulong.com/openapi/lesson/getCarouselphoto")
                        .post(build)
                        .build();
                okHttpClient.newCall(build1).enqueue(new Callback() {
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
            case ApiConfig.GET_HOME_LIST_DATA:
                int loadType = (int) params[0];
                Map<String,Object> paramHashMap = (ParamHashMap) params[1];
                netManger.netWork(netManger.getService(mContext.getString(R.string.edu_openapi)).getLiveData(paramHashMap), presenter, whichApi,loadType);
                break;
        }
    }
}
