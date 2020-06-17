package com.hdj.base;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.hdj.data.SpecialtyChooseEntity;
import com.hdj.frame.IContract;
import com.hdj.frame.MyPresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMvpFragment<M extends IContract.IModel> extends BaseFragment implements IContract.IView {
    private M mModel;
    public MyPresenter mPresenter;
    private Unbinder mBind;
    public Context mContext;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(setLayoutId(), container, false);
        mBind = ButterKnife.bind(this, root);
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        mModel = setModel();
        mPresenter = new MyPresenter(this, mModel);
        setUpView();
        setUpData();
    }

    protected abstract int setLayoutId();

    public abstract M setModel();

    public abstract void setUpView();

    public abstract void setUpData();

    public abstract void netSuccess(int whichApi, Object[] pD);

    public void netFailed(int whichApi, Throwable pThrowable) {
    }

    @Override
    public void onSuccess(int whichApi, Object[] pD) {
        netSuccess(whichApi, pD);
    }

    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        showLog("net work error: " + whichApi + "error content" + pThrowable != null && !TextUtils.isEmpty(pThrowable.getMessage()) ? pThrowable.getMessage() : "不明错误类型");
        netFailed(whichApi, pThrowable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.clear();
        if (mBind != null) mBind.unbind();
    }


}
