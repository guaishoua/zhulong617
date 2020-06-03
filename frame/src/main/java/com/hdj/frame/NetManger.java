package com.hdj.frame;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 任小龙 on 2019/12/19.
 */
public class NetManger {
    private NetManger() {
    }

    private static volatile NetManger sNetManger;

    public static NetManger getInstance() {
        if (sNetManger == null) {
            synchronized (NetManger.class) {
                sNetManger = new NetManger();
            }
        }
        return sNetManger;
    }

    /**
     * @param t   如果传baseURL，用传过来，没传的话用默认
     * @param <T>
     * @return
     */
    public <T> ApiService getService(T... t) {
        String baseUrl = ServerAddressConfig.BASE_URL;
        if (t != null && t.length != 0) {
            baseUrl = (String) t[0];
        }
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(initClient())
                .build()
                .create(ApiService.class);
    }

    private OkHttpClient initClient() {
        return new OkHttpClient().newBuilder()
                .addInterceptor(new CommonHeadersInterceptor())
                .addInterceptor(new CommonParamsInterceptor())
                .addInterceptor(initLogInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    private Interceptor initLogInterceptor() {
        HttpLoggingInterceptor log = new HttpLoggingInterceptor();
        log.setLevel(HttpLoggingInterceptor.Level.BODY);
        return log;
    }


    public <T, O> void netWork(Observable<T> localTestInfo, final IContract.IPresenter pPresenter, final int whichApi, final O... o) {
        localTestInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        pPresenter.addObserver(d);
                    }

                    @Override
                    public void onSuccess(Object value) {
                        pPresenter.onSuccess(whichApi, value, o);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        pPresenter.onFailed(whichApi, e);
                    }
                });
    }


}
