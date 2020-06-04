package com.hdj.model;

import android.content.Context;



import com.hdj.base.Application1907;
import com.hdj.frame.ApiConfig;
import com.hdj.frame.FrameApplication;
import com.hdj.frame.IContract;
import com.hdj.frame.NetManger;
import com.hdj.util.ParamHashMap;
import com.hdj.zhulong.R;


public class AccountModel implements IContract.IModel {
    private NetManger mManger = NetManger.getInstance();
    private Context mContext = Application1907.get07ApplicationContext();

    @Override
    public void getData(IContract.IPresenter presenter, int whichApi, Object[] params) {
        switch (whichApi) {
            case ApiConfig.SEND_VERIFY:
                mManger.netWork(mManger.getService(mContext.getString(R.string.passport_openapi_user)).getLoginVerify((String) params[0]), presenter, whichApi);
                break;
            case ApiConfig.VERIFY_LOGIN:
                mManger.netWork(mManger.getService(mContext.getString(R.string.passport_openapi_user)).loginByVerify(new ParamHashMap().add("mobile",params[0]).add("code",params[1])),presenter,whichApi);
                break;
            case ApiConfig.GET_HEADER_INFO:
                String uid = FrameApplication.getFrameApplication().getLoginInfo().getUid();
                mManger.netWork(mManger.getService(mContext.getString(R.string.passport_api)).getHeaderInfo(new ParamHashMap().add("zuid",uid).add("uid",uid)),presenter,whichApi);
                break;
        }
    }
}
