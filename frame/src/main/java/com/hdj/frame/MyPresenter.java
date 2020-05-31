package com.hdj.frame;

import java.lang.ref.SoftReference;

public class MyPresenter<V extends IContract.IView, M extends IContract.IModel> implements IContract.IPresenter {
    private SoftReference<V> mView;
    private SoftReference<M> mModel;

    public MyPresenter(V pView, M pModel) {
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
    public void getData(int whichApi, Object... p) {
        if (mModel != null && mModel.get() != null)mModel.get().getData(this,whichApi,p);
    }

    public void clear() {
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
