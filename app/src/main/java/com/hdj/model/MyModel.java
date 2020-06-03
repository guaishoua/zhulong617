package com.hdj.model;

import android.content.Context;

import com.hdj.base.Application1907;
import com.hdj.data.AdvertBean;
import com.hdj.frame.ApiConfig;
import com.hdj.frame.ApiService;
import com.hdj.frame.IContract;
import com.hdj.frame.NetManger;
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
                new Retrofit.Builder()
                        .baseUrl(ApiService.url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(new OkHttpClient())
                        .build().create(ApiService.class)
                        .getAdvertData((String) params[0], (String) params[1])
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<AdvertBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(AdvertBean advertBean) {
                                presenter.onSuccess(whichApi, advertBean);
                            }

                            @Override
                            public void onError(Throwable e) {
                                presenter.onFailed(whichApi, e);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            case ApiConfig.SUBJECT:
                mManger.netWork(mManger.getService(mContext.getString(R.string.edu_openapi)).getSubjectList(), presenter, whichApi);
                break;
        }
    }
}
