package com.hdj.model;

import android.content.Context;

import com.hdj.base.Application1907;
import com.hdj.contants.Method;
import com.hdj.frame.ApiConfig;
import com.hdj.frame.FrameApplication;
import com.hdj.frame.Host;
import com.hdj.frame.IContract;
import com.hdj.frame.NetManger;
import com.hdj.util.ParamHashMap;
import com.hdj.zhulong.R;


import java.util.Map;

public class DataModel implements IContract.IModel {
    NetManger netManger=NetManger.getInstance();
    private Context mContext = Application1907.get07ApplicationContext();
    @Override
    public void getData(IContract.IPresenter presenter, int whichApi, Object[] params) {
        switch (whichApi) {
            case ApiConfig.DATA_GROUP:
                Map<String, Object> map = (ParamHashMap) params[1];
                int loadType = (int) params[0];
                netManger.netWork(netManger.getService(mContext.getString(R.string.bbs_openapi)).getGroupData(map), presenter, whichApi,loadType);
                break;
            case ApiConfig.DATA_RECENTLY:
                map = (ParamHashMap) params[0];
                loadType = (int) params[1];
                netManger.netWork(netManger.getService(mContext.getString(R.string.bbs_openapi)).getRecentlyData(map),presenter,whichApi,loadType);
                break;
            case ApiConfig.CLICK_CANCEL_FOCUS:
                ParamHashMap add1 = new ParamHashMap().add("group_id", params[0]).add("type", 1).add("screctKey", FrameApplication.getFrameApplicationContext().getString(R.string.secrectKey_posting));
                netManger.netWork(netManger.getService().removeFocus(Host.BBS_API+ Method.REMOVEGROUP,add1),presenter,whichApi,(int)params[1]);
                break;
            case ApiConfig.CLICK_TO_FOCUS:
                ParamHashMap add2 = new ParamHashMap().add("gid", params[0]).add("group_name", params[1]).add("screctKey", FrameApplication.getFrameApplicationContext().getString(R.string.secrectKey_posting));
                netManger.netWork(netManger.getService().focus(Host.BBS_API+Method.JOINGROUP,add2),presenter,whichApi,(int)params[2]);
                break;
            case ApiConfig.GROUP_DETAIL:
                NetManger.getInstance().netWork(netManger.getService().getGroupDetail(Host.BBS_OPENAPI+Method.GETGROUPTHREADLIST,params[0]),presenter,whichApi);
                break;
            case ApiConfig.GROUP_DETAIL_FOOTER_DATA:
                NetManger.getInstance().netWork(netManger.getService().getGroupDetailFooterData(Host.BBS_OPENAPI+Method.GETGROUPTHREADLIST, (Map<String, Object>) params[1]),presenter,whichApi,(int)params[0]);
                break;
        }
    }
}
