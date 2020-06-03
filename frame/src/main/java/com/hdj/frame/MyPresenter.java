package com.hdj.frame;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class MyPresenter<V extends IContract.IView, M extends IContract.IModel> implements IContract.IPresenter {
    private SoftReference<V> mView;
    private SoftReference<M> mModel;
    private List<Disposable> mDisposableList;
    public MyPresenter(V pView, M pModel) {
        mDisposableList = new ArrayList<>();
        this.mView = new SoftReference<>(pView);
        this.mModel = new SoftReference<>(pModel);
    }

    @Override
    public void onSuccess(int whichApi, Object[] params) {
        if (mView != null && mView.get() != null) mView.get().onSuccess(whichApi, params);
    }

    @Override
    public void onFailed(int whichApi, Throwable throwable) {
        if (mView != null && mView.get() != null) mView.get().onFailed(whichApi, throwable);
    }

    @Override
    public void addObserver(Disposable pDisposable) {
        if(mDisposableList==null)return;
        mDisposableList.add(pDisposable);
    }

    @Override
    public void getData(int whichApi, Object... p) {
        if (mModel != null && mModel.get() != null)mModel.get().getData(this,whichApi,p);
    }

    public void clear() {
        for (Disposable dis:mDisposableList) {
            if (dis != null && !dis.isDisposed())dis.dispose();
        }
        if (mModel != null) {
            mModel.clear();
            mModel = null;
        }
        if (mView != null) {
            mView.clear();
            mView = null;
        }
    }
}
