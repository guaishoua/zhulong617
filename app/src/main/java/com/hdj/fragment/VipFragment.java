package com.hdj.fragment;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hdj.adater.MainliveAdapter;
import com.hdj.adater.VipAdapter;
import com.hdj.adater.VipListAdapter;
import com.hdj.adater.VipliveAdapter;
import com.hdj.base.Application1907;
import com.hdj.base.BaseMvpFragment;
import com.hdj.data.BaseInfo;
import com.hdj.data.VipBannerBean;
import com.hdj.data.VipListBean;
import com.hdj.frame.ApiConfig;
import com.hdj.frame.ApiService;
import com.hdj.frame.LoadTypeConfig;
import com.hdj.interfaces.DataListener;
import com.hdj.model.VipModel;
import com.hdj.util.ParamHashMap;
import com.hdj.utils.GsonUtil;
import com.hdj.zhulong.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class VipFragment extends BaseMvpFragment<VipModel> {


    @BindView(R.id.vip_recyclerView)
    RecyclerView vip_recyclerView;

    @BindView(R.id.main_page_smart)
    SmartRefreshLayout mainPageSmart;

    private String mSpecialty_id;
    private String mUid;

    private int page = 1;
    private VipAdapter mVipAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_vip;
    }

    @Override
    public VipModel setModel() {
        return new VipModel();
    }

    @Override
    public void setUpView() {
        mSpecialty_id = Application1907.getFrameApplication().getSelectedInfo().getSpecialty_id();
        mUid = Application1907.getFrameApplication().getLoginInfo().getUid();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == 1) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        vip_recyclerView.setLayoutManager(gridLayoutManager);
        mVipAdapter = new VipAdapter(mContext);
        vip_recyclerView.setAdapter(mVipAdapter);
    }

    @Override
    public void setUpData() {
        ParamHashMap liveMap = new ParamHashMap().add("pro", mSpecialty_id).add("uid", mUid);
        mPresenter.getData(ApiConfig.GET_VIP_LIVE_DATA, liveMap);

        ParamHashMap listMap = new ParamHashMap().add("specialty_id", mSpecialty_id).add("page", page).add("sort", 1);
        mPresenter.getData(ApiConfig.GET_VIP_LIST_DATA, listMap, LoadTypeConfig.NORMAL);

        initRecyclerView(vip_recyclerView, mainPageSmart, new DataListener() {
            @Override
            public void dataType(int mode) {
                if (mode == LoadTypeConfig.REFRESH) {
                    page = 1;
                    ParamHashMap listMap = new ParamHashMap().add("specialty_id", mSpecialty_id).add("page", page).add("sort", 1);
                    mPresenter.getData(ApiConfig.GET_VIP_LIST_DATA, listMap, LoadTypeConfig.REFRESH);
                } else if (mode == LoadTypeConfig.MORE) {
                    page++;
                    ParamHashMap listMap = new ParamHashMap().add("specialty_id", mSpecialty_id).add("page", page).add("sort", 1);
                    mPresenter.getData(ApiConfig.GET_VIP_LIST_DATA, listMap, LoadTypeConfig.NORMAL);
                }
            }
        });
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.GET_VIP_LIST_DATA:
                BaseInfo<VipListBean> list = (BaseInfo<VipListBean>) pD[0];
                Integer[] integers = (Integer[]) pD[1];
                int loadType = integers[0].intValue();
                List<VipListBean.ListBean> result = list.result.getList();
                if (loadType == LoadTypeConfig.REFRESH) {
                    mainPageSmart.finishRefresh();
                    mVipAdapter.setReList(result);
                } else if (loadType == LoadTypeConfig.MORE) {
                    mainPageSmart.finishLoadMore();
                    mVipAdapter.setList(result);
                } else if (loadType == LoadTypeConfig.NORMAL) {
                    mVipAdapter.setList(result);
                }

                break;
            case ApiConfig.GET_VIP_LIVE_DATA:
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String s = (String) pD[0];
                        JSONObject object = null;
                        try {
                            object = new JSONObject(s).optJSONObject("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONArray object_list = object.optJSONArray("lunbotu");
                        List<VipBannerBean.ResultBean.LunbotuBean> lunbotuBeans = GsonUtil.jsonToList(object_list.toString(), VipBannerBean.ResultBean.LunbotuBean.class);
                        if (lunbotuBeans != null && lunbotuBeans.size() != 0) {
                            mVipAdapter.setBanners(lunbotuBeans);
                        }
                        JSONObject live = object.optJSONObject("live");
                        int count = live.optInt("count");
                        if (count == 0) {
//                            liveView.setVisibility(View.GONE);
//                            views.setVisibility(View.GONE);
                        } else {
                            JSONArray live1 = live.optJSONArray("live");
                            List<VipBannerBean.ResultBean.LiveBeanX.LiveBean> liveBeans = GsonUtil.jsonToList(live1.toString(), VipBannerBean.ResultBean.LiveBeanX.LiveBean.class);
                            mVipAdapter.setLiveingEntitys(liveBeans);
                        }
                    }
                });
                break;
        }
    }
}
