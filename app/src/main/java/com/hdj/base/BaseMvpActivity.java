package com.hdj.base;

import android.os.Bundle;

import com.hdj.frame.IContract;
import com.hdj.frame.MyPresenter;
import com.hdj.model.MyModel;

public abstract class BaseMvpActivity<M extends MyModel> extends BaseActivity implements IContract.IView {
    private M model;
    public MyPresenter myPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        butterknife.ButterKnife.bind(this);
        model = initModel();
        myPresenter = new MyPresenter(this, model);
        initView();
        initData();
    }

    protected abstract M initModel();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayout();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myPresenter.clear();
    }

    protected abstract void netSuccess(int whichApi, Object[] params);

    protected abstract void netFailed(int whichApi, Throwable throwable);

    @Override
    public void onFailed(int whichApi, Throwable throwable) {
        netFailed(whichApi,throwable);
    }

    @Override
    public void onSuccess(int whichApi, Object[] params) {
        netSuccess(whichApi,params);
    }
}
