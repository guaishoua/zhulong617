package com.hdj.fragment;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hdj.adater.DataGroupAdapter;
import com.hdj.adater.RecentlyAdapter;
import com.hdj.base.Application1907;
import com.hdj.base.BaseMvpFragment;
import com.hdj.data.BaseInfo;
import com.hdj.data.DataGroupBean;
import com.hdj.data.RecentlyBean;
import com.hdj.frame.ApiConfig;
import com.hdj.frame.LoadTypeConfig;
import com.hdj.interfaces.DataListener;
import com.hdj.model.DataModel;
import com.hdj.util.ParamHashMap;
import com.hdj.zhulong.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentlyBestFragment extends BaseMvpFragment<DataModel> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    private int fid;
    private int page =1;
    private RecentlyAdapter mRecentlyAdapter;

    public static RecentlyBestFragment newInstance() {
        RecentlyBestFragment fragment = new RecentlyBestFragment();
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_jing_pin;
    }

    @Override
    public DataModel setModel() {
        return new DataModel();
    }

    @Override
    public void setUpView() {
        fid = Application1907.getFrameApplication().getSelectedInfo().getFid();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecentlyAdapter = new RecentlyAdapter(mContext);
        recyclerView.setAdapter(mRecentlyAdapter);
    }

    @Override
    public void setUpData() {
        ParamHashMap add = new ParamHashMap().add("fid", fid).add("page", page);
        mPresenter.getData(ApiConfig.DATA_RECENTLY, add, LoadTypeConfig.NORMAL);
        initRecyclerView(recyclerView, smartRefresh, new DataListener() {
            @Override
            public void dataType(int mode) {
                if(mode == LoadTypeConfig.REFRESH){
                    page = 1;
                    ParamHashMap add = new ParamHashMap().add("fid", fid).add("page", page);
                    mPresenter.getData(ApiConfig.DATA_RECENTLY, add, LoadTypeConfig.REFRESH);
                }else if(mode == LoadTypeConfig.MORE){
                    page++;
                    ParamHashMap add = new ParamHashMap().add("fid", fid).add("page", page);
                    mPresenter.getData(ApiConfig.DATA_RECENTLY, add, LoadTypeConfig.MORE);
                }
            }
        });
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.DATA_RECENTLY:
                BaseInfo<List<RecentlyBean>> info = (BaseInfo<List<RecentlyBean>>) pD[0];
                if (info.isSuccess()) {
                    List<RecentlyBean> result = info.result;
                    Integer[] integers = (Integer[]) pD[1];
                    int loadType = integers[0].intValue();
                    if (loadType == LoadTypeConfig.REFRESH) {
                        smartRefresh.finishRefresh();
                        mRecentlyAdapter.setReData(result);
                    } else if (loadType == LoadTypeConfig.MORE){
                        smartRefresh.finishLoadMore();
                        mRecentlyAdapter.setData(result);
                    }else {
                        mRecentlyAdapter.setData(result);
                    }
                }
                break;
        }
    }
}
