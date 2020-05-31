package com.hdj.frame;

import com.hdj.data.AdvertBean;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyModel implements IContract.IModel{
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
                                presenter.onSuccess(whichApi,advertBean);
                            }

                            @Override
                            public void onError(Throwable e) {
                                presenter.onFailed(whichApi,e);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
        }
    }
}
