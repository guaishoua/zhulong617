package com.hdj.model;

import android.content.Context;



import com.hdj.base.Application1907;
import com.hdj.constans.ConstantKey;
import com.hdj.contants.Method;
import com.hdj.data.ThirdLoginData;
import com.hdj.frame.ApiConfig;
import com.hdj.frame.FrameApplication;
import com.hdj.frame.Host;
import com.hdj.frame.IContract;
import com.hdj.frame.NetManger;
import com.hdj.secret.RsaUtil;
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
            case ApiConfig.REGISTER_PHONE:
                mManger.netWork(mManger.getService().checkVerifyCode(Host.PASSPORT_API+ Method.CHECKMOBILECODE,new ParamHashMap().add("mobile",params[0]).add("code",params[1])),presenter,whichApi);
                break;
            case ApiConfig.CHECK_PHONE_IS_USED:
                mManger.netWork(mManger.getService().checkPhoneIsUsed(Host.PASSPORT_API+Method.CHECKMOBILEISUSE,params[0]),presenter,whichApi);
                break;
            case ApiConfig.SEND_REGISTER_VERIFY:
                mManger.netWork(mManger.getService().sendRegisterVerify(Host.PASSPORT_API+Method.SENDMOBILECODE,params[0]),presenter,whichApi);
                break;
            case ApiConfig.NET_CHECK_USERNAME:
                mManger.netWork(mManger.getService().checkName(Host.PASSPORT+Method.USERNAMEISEXIST,params[0]),presenter,whichApi);
                break;
            case ApiConfig.COMPLETE_REGISTER_WITH_SUBJECT:
                ParamHashMap param = new ParamHashMap().add("username", params[0]).add("password", RsaUtil.encryptByPublic((String) params[1]))
                        .add("tel", params[2]).add("specialty_id", FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id())
                        .add("province_id", 0).add("city_id", 0).add("sex", 0).add("from_reg_name", 0).add("from_reg", 0);
                mManger.netWork(mManger.getService().registerCompleteWithSubject(Host.PASSPORT_API+Method.USERREGFORSIMPLE,param),presenter,whichApi);
                break;
            case ApiConfig.ACCOUNT_LOGIN:
                ParamHashMap add = new ParamHashMap().add("ZLSessionID", "").add("seccode", "").add("loginName", params[0])
                        .add("passwd", RsaUtil.encryptByPublic((String) params[1])).add("cookieday", "")
                        .add("fromUrl", "android").add("ignoreMobile", "0");
                mManger.netWork(mManger.getService().loginByAccount(Host.PASSPORT_OPENAPI+Method.USERLOGINNEWAUTH,add),presenter,whichApi);
                break;
            case ApiConfig.GET_WE_CHAT_TOKEN:
                ParamHashMap wxParams = new ParamHashMap().add("appid", ConstantKey.WX_APP_ID).add("secret", ConstantKey.WX_APP_SECRET).add("code", params[0]).add("grant_type", "authorization_code");
                mManger.netWork(mManger.getService().getWechatToken(Host.WX_OAUTH+Method.ACCESS_TOKEN,wxParams),presenter,whichApi);
                break;
            case ApiConfig.POST_WE_CHAT_LOGIN_INFO:
                ThirdLoginData data = (ThirdLoginData) params[0];
                ParamHashMap add1 = new ParamHashMap().add("openid", data.openid).add("type", data.type).add("url", "android");
                mManger.netWork(mManger.getService().loginByWechat(Host.PASSPORT_API+Method.THIRDLOGIN,add1),presenter,whichApi);
                break;
            case ApiConfig.BIND_ACCOUNT:
                String account = (String) params[0];
                String password = (String) params[1];
                ThirdLoginData thirdLoginData = (ThirdLoginData) params[2];
                ParamHashMap thirdDataParam = new ParamHashMap().add("username", account).add("password", RsaUtil.encryptByPublic(password))
                        .add("openid", thirdLoginData.openid).add("t_token", thirdLoginData.token)
                        .add("utime", thirdLoginData.utime).add("type", thirdLoginData.type)
                        .add("url", "android").add("state", 1);
                mManger.netWork(mManger.getService().bindThirdAccount(Host.PASSPORT_API+Method.NEWTHIRDBIND,thirdDataParam),presenter,whichApi);
                break;
        }
    }
}
