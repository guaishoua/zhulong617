package com.hdj.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hdj.adater.DataGroupAdapter;
import com.hdj.base.Application1907;
import com.hdj.base.BaseMvpFragment;
import com.hdj.constans.ConstantKey;
import com.hdj.data.BaseInfo;
import com.hdj.data.DataGroupBean;
import com.hdj.data.RecentlyBean;
import com.hdj.frame.ApiConfig;
import com.hdj.frame.FrameApplication;
import com.hdj.frame.LoadTypeConfig;
import com.hdj.interfaces.DataListener;
import com.hdj.interfaces.OnRecyclerItemClick;
import com.hdj.model.DataModel;
import com.hdj.util.ParamHashMap;
import com.hdj.zhulong.HomeActivity;
import com.hdj.zhulong.LoginActivity;
import com.hdj.zhulong.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

import static com.hdj.adater.DataGroupAdapter.FOCUS_TYPE;
import static com.hdj.adater.DataGroupAdapter.ITEM_TYPE;
import static com.hdj.contants.JumpConstant.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataGroupFragment extends BaseMvpFragment<DataModel> implements DataListener, OnRecyclerItemClick {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    private int page = 1;
    private DataGroupAdapter mDataGroupAdapter;

    public static DataGroupFragment newInstance() {
        DataGroupFragment fragment = new DataGroupFragment();
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
        initRecyclerView(recyclerView, smartRefresh, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataGroupAdapter = new DataGroupAdapter(mContext);
        recyclerView.setAdapter(mDataGroupAdapter);
        mDataGroupAdapter.setOnRecyclerItemClick(this);
    }

    @Override
    public void setUpData() {
        ParamHashMap add = new ParamHashMap().add("page", page).add("fid", Application1907.getFrameApplication().getSelectedInfo().getFid());
        mPresenter.getData(ApiConfig.DATA_GROUP, LoadTypeConfig.NORMAL, add);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.DATA_GROUP:
                BaseInfo<List<DataGroupBean>> info = (BaseInfo<List<DataGroupBean>>) pD[0];
                if (info.isSuccess()) {
                    List<DataGroupBean> recentlyBeans = info.result;
                    Integer[] integers = (Integer[]) pD[1];
                    int loadType = integers[0].intValue();
                    if (loadType == LoadTypeConfig.REFRESH) {
                        smartRefresh.finishRefresh();
                        mDataGroupAdapter.setReList(recentlyBeans);
                    } else if (loadType == LoadTypeConfig.MORE) {
                        smartRefresh.finishLoadMore();
                        mDataGroupAdapter.setList(recentlyBeans);
                    } else {
                        mDataGroupAdapter.setList(recentlyBeans);
                    }
                }
                break;
            case ApiConfig.CLICK_CANCEL_FOCUS:
                BaseInfo base = (BaseInfo) pD[0];
                Integer[] integers = (Integer[]) pD[1];
                int clickPos = integers[0];
                if (base.isSuccess()){
                    showToast("取消成功");
                    mDataGroupAdapter.getList().get(clickPos).setIs_ftop(0);
                    mDataGroupAdapter.notifyItemChanged(clickPos);
                }
                break;
            case ApiConfig.CLICK_TO_FOCUS:
                BaseInfo baseSuc = (BaseInfo) pD[0];
                Integer[] integer = (Integer[]) pD[1];
                int clickJoinPos = integer[0];
                if (baseSuc.isSuccess()){
                    showToast("关注成功");
                    mDataGroupAdapter.getList().get(clickJoinPos).setIs_ftop(1);
                    mDataGroupAdapter.notifyItemChanged(clickJoinPos);
                }
                break;
        }
    }

    @Override
    public void dataType(int mode) {
        if (mode == LoadTypeConfig.REFRESH) {
            ParamHashMap add = new ParamHashMap().add("page", 1).add("fid", Application1907.getFrameApplication().getSelectedInfo().getFid());
            mPresenter.getData(ApiConfig.DATA_GROUP, LoadTypeConfig.REFRESH, add);
        } else {
            page++;
            ParamHashMap add = new ParamHashMap().add("page", page).add("fid", Application1907.getFrameApplication().getSelectedInfo().getFid());
            mPresenter.getData(ApiConfig.DATA_GROUP, LoadTypeConfig.MORE, add);
        }
    }

    @Override
    public void onItemClick(int pos, Object[] pObjects) {
        if (pObjects != null && pObjects.length != 0) {
            switch ((int) pObjects[0]) {
                case ITEM_TYPE:
                    HomeActivity activity = (HomeActivity) getActivity();
                    Bundle bundle = new Bundle();
                    bundle.putString(ConstantKey.GROU_TO_DETAIL_GID,mDataGroupAdapter.getDataGroupBean(pos).getGid());
                    activity.mProjectController.navigate(R.id.dataGroupDetailFragment,bundle);
                    break;
                case FOCUS_TYPE:
                    boolean login = FrameApplication.getFrameApplication().isLogin();
                    if (login) {
                        DataGroupBean entity = mDataGroupAdapter.getDataGroupBean(pos);
                        if (entity.isFocus()) {//已经关注，取消关注
                            mPresenter.getData(ApiConfig.CLICK_CANCEL_FOCUS, entity.getGid(), pos);//绿码
                        } else {//没有关注，点击关注
                            mPresenter.getData(ApiConfig.CLICK_TO_FOCUS, entity.getGid(), entity.getGroup_name(), pos);
                        }
                    }else {
                        startActivity(new Intent(getContext(), LoginActivity.class).putExtra(JUMP_KEY,DATAGROUPFRAGMENT_TO_LOGIN));
                    }
                    break;
            }
        }
    }
}
