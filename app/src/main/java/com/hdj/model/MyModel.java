package com.hdj.model;

import android.content.Context;
import android.text.TextUtils;

import com.hdj.base.Application1907;
import com.hdj.data.AdvertBean;
import com.hdj.frame.ApiConfig;
import com.hdj.frame.ApiService;
import com.hdj.frame.IContract;
import com.hdj.frame.NetManger;
import com.hdj.util.ParamHashMap;
import com.hdj.zhulong.R;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyModel implements IContract.IModel {


    private NetManger mManger = NetManger.getInstance();
    private Context mContext = Application1907.get07ApplicationContext();

    @Override
    public void getData(final IContract.IPresenter presenter, final int whichApi, Object[] params) {
        switch (whichApi) {
            case ApiConfig.TEST_POST:
                ParamHashMap map = new ParamHashMap().add("w", params[1]).add("h", params[2]).add("positions_id", "APP_QD_01").add("is_show", 0);
                if (!TextUtils.isEmpty((String) params[0])) map.add("specialty_id", params[0]);
                mManger.netWork(mManger.getService(mContext.getString(R.string.ad_openapi)).getAdvertData(map), presenter, whichApi);
                break;
            case ApiConfig.SUBJECT:
                mManger.netWork(mManger.getService(mContext.getString(R.string.edu_openapi)).getSubjectList(), presenter, whichApi);
                break;
        }
    }
}
