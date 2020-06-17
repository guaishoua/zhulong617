package com.hdj.fragment;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hdj.adater.MainListAdapter;
import com.hdj.adater.MainPageAdapter;
import com.hdj.adater.MainliveAdapter;
import com.hdj.base.Application1907;
import com.hdj.base.BaseMvpFragment;
import com.hdj.data.BaseInfo;
import com.hdj.data.HomeBannerBean;
import com.hdj.data.HomeliveBean;
import com.hdj.data.SpecialtyChooseEntity;
import com.hdj.frame.ApiConfig;
import com.hdj.frame.LoadTypeConfig;
import com.hdj.interfaces.DataListener;
import com.hdj.model.MainPageModel;
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


public class MainPageFragment extends BaseMvpFragment<MainPageModel> {



    @BindView(R.id.main_page_smart)
    SmartRefreshLayout mainPageSmart;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private int page = 1;
    public String mSpecialty_id;
    private MainPageAdapter mMainPageAdapter;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_main_page;
    }

    @Override
    public MainPageModel setModel() {
        return new MainPageModel();
    }


    @Override
    public void setUpView() {
        SpecialtyChooseEntity.DataBean selectedInfo = Application1907.getFrameApplication().getSelectedInfo();
        mSpecialty_id = selectedInfo.getSpecialty_id();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mMainPageAdapter = new MainPageAdapter(mContext);
        recyclerView.setAdapter(mMainPageAdapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && recyclerView != null && recyclerView != null) {
            SpecialtyChooseEntity.DataBean selectedInfo = Application1907.getFrameApplication().getSelectedInfo();
            mSpecialty_id = selectedInfo.getSpecialty_id();
        }
    }



    @Override
    public void setUpData() {
        ParamHashMap paramHashMap = new ParamHashMap().add("pro", mSpecialty_id).add("more_live", "1").add("is_new", "1").add("new_banner", "1");
        mPresenter.getData(ApiConfig.GET_HOME_LIVE_DATA, paramHashMap);

        ParamHashMap map = new ParamHashMap().add("specialty_id", mSpecialty_id).add("page", page).add("limit", 10);
        mPresenter.getData(ApiConfig.GET_HOME_LIST_DATA, LoadTypeConfig.NORMAL, map);
        initRecyclerView(recyclerView, mainPageSmart, new DataListener() {
            @Override
            public void dataType(int mode) {
                if (mode == LoadTypeConfig.REFRESH) {
                    page = 1;
                    ParamHashMap map = new ParamHashMap().add("specialty_id", mSpecialty_id).add("page", page).add("limit", 10);
                    mPresenter.getData(ApiConfig.GET_HOME_LIST_DATA, LoadTypeConfig.REFRESH, map);
                } else if (mode == LoadTypeConfig.MORE) {
                    page++;
                    ParamHashMap map = new ParamHashMap().add("specialty_id", mSpecialty_id).add("page", page).add("limit", 10);
                    mPresenter.getData(ApiConfig.GET_HOME_LIST_DATA, LoadTypeConfig.MORE, map);
                }
            }
        });
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.GET_HOME_LIVE_DATA:
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String s = (String) pD[0];
                        JSONObject object = null;
                        try {
                            object = new JSONObject(s).optJSONObject("result");

                            JSONArray object_list = object.optJSONArray("Carousel");
                            List<HomeBannerBean.ResultBean.CarouselBean> list = GsonUtil.jsonToList(object_list.toString(), HomeBannerBean.ResultBean.CarouselBean.class);
                            if (list != null && list.size() != 0) {
                                mMainPageAdapter.setBanners(list);
                            }

                            List<HomeBannerBean.ResultBean.LiveBean> liveingEntitys = GsonUtil.jsonToList(object.optString("live"), HomeBannerBean.ResultBean.LiveBean.class);
                            if (liveingEntitys != null && liveingEntitys.size() != 0) {
                                mMainPageAdapter.setLiveingEntitys(liveingEntitys);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case ApiConfig.GET_HOME_LIST_DATA:

                Integer[] integers = (Integer[]) pD[1];

                int loadType = integers[0].intValue();

                BaseInfo<List<HomeliveBean>> bean = (BaseInfo<List<HomeliveBean>>) pD[0];
                List<HomeliveBean> result = bean.result;
                Log.d("result", "netSuccess: " + result);
                if (loadType == LoadTypeConfig.REFRESH) {
                    mainPageSmart.finishRefresh();
                    mMainPageAdapter.setReList(result);
                } else if (loadType == LoadTypeConfig.MORE) {
                    mainPageSmart.finishLoadMore();
                    mMainPageAdapter.setList(result);
                } else if (loadType == LoadTypeConfig.NORMAL) {
                    mMainPageAdapter.setList(result);
                }
                break;
        }
    }
}
