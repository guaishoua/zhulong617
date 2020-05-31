package com.hdj.frame;

public interface IContract {
    interface IView<T>{
        void onSuccess(int whichApi,T... params);
        void onFailed(int whichApi,Throwable throwable);
    }

    interface IPresenter<P> extends IView{
        void getData(int whichApi,P...p);
    }
    interface IModel<T>{
        void getData(IPresenter presenter,int whichApi,T... params);
    }
}
