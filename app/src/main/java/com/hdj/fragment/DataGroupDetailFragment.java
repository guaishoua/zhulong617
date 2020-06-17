package com.hdj.fragment;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.hdj.adater.DataGroupDetailBottomAdapter;
import com.hdj.adater.GroupDetailCenterTabAdapter;
import com.hdj.base.BaseMvpFragment;
import com.hdj.constans.ConstantKey;
import com.hdj.constans.Constants;
import com.hdj.data.BaseInfo;
import com.hdj.data.GroupDetailEntity;
import com.hdj.frame.ApiConfig;
import com.hdj.frame.LoadTypeConfig;
import com.hdj.interfaces.DataListener;
import com.hdj.model.DataModel;
import com.hdj.util.ParamHashMap;
import com.hdj.utils.newAdd.GlideUtil;
import com.hdj.zhulong.HomeActivity;
import com.hdj.zhulong.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DataGroupDetailFragment extends BaseMvpFragment<DataModel> implements DataListener {
    @BindView(R.id.image_back)
    ImageView imageBack;
    @BindView(R.id.iv_thumb)
    ImageView ivThumb;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_member_num)
    TextView tvMemberNum;
    @BindView(R.id.tv_post_num)
    TextView tvPostNum;
    @BindView(R.id.tv_focus)
    TextView tvFocus;
    @BindView(R.id.groupBack)
    ImageView groupBack;
    @BindView(R.id.groupTitle)
    TextView groupTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabRecycle)
    RecyclerView tabRecycle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    private String mGid;

    private List<GroupDetailEntity.Tag> mTabListData = new ArrayList<>();
    private DataGroupDetailBottomAdapter mDataGroupDetailBottomAdapter;
    private GroupDetailCenterTabAdapter mGroupDetailCenterTabAdapter;
    private HomeActivity mActivity;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_data_group_detail;
    }

    @Override
    public DataModel setModel() {
        return new DataModel();
    }

    @Override
    public void setUpView() {
        mActivity = (HomeActivity) getActivity();
        if (getArguments() != null) {
            mGid = getArguments().getString(ConstantKey.GROU_TO_DETAIL_GID);
        }
        groupTitle.setVisibility(View.GONE);
        appBar.addOnOffsetChangedListener((pAppBarLayout, verticalOffset) -> {
            boolean space = Math.abs(verticalOffset) >= tvName.getBottom();
            groupTitle.setVisibility(space ? View.VISIBLE : View.GONE);
            toolbar.setBackgroundColor(space ? setColor(R.color.app_theme) : Color.TRANSPARENT);
        });
        initRecyclerView(recyclerView, refreshLayout, this);
        mDataGroupDetailBottomAdapter = new DataGroupDetailBottomAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mDataGroupDetailBottomAdapter);
        LinearLayoutManager ma = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        tabRecycle.setLayoutManager(ma);
        mGroupDetailCenterTabAdapter = new GroupDetailCenterTabAdapter(getContext(), mTabListData);
        tabRecycle.setAdapter(mGroupDetailCenterTabAdapter);
    }

    @Override
    public void setUpData() {
        mPresenter.getData(ApiConfig.GROUP_DETAIL, mGid);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.GROUP_DETAIL:
                BaseInfo<GroupDetailEntity> baseInfo = (BaseInfo<GroupDetailEntity>) pD[0];
                if (baseInfo.isSuccess()) {
                    GroupDetailEntity groupDetailEntity = baseInfo.result;
                    setDetailData(groupDetailEntity);
                    getFooterData(LoadTypeConfig.NORMAL);
                }
                break;
            case ApiConfig.GROUP_DETAIL_FOOTER_DATA:
                BaseInfo<GroupDetailEntity> base = (BaseInfo<GroupDetailEntity>) pD[0];
                if (base.isSuccess()) {
                    Integer[] integers = (Integer[]) pD[1];
                    int loadType = (int) integers[0];
                    ArrayList<GroupDetailEntity.Thread> thread_list = base.result.getThread_list();
                    if (loadType == LoadTypeConfig.REFRESH) {
                        refreshLayout.finishRefresh();
                        mDataGroupDetailBottomAdapter.setReList(thread_list);
                    } else if (loadType == LoadTypeConfig.MORE) {
                        refreshLayout.finishLoadMore();
                        if (base.result.getThread_list().size() < Constants.LIMIT_NUM)refreshLayout.setNoMoreData(true);
                        mDataGroupDetailBottomAdapter.setList(thread_list);
                    }else {
                        mDataGroupDetailBottomAdapter.setList(thread_list);
                    }
                }
                break;
        }
    }


    private String tags = "";

    private void getFooterData(int pNormal) {
        ParamHashMap add = new ParamHashMap().add("gid", mGid).add("page", page).add("limit", Constants.LIMIT_NUM);
        if (!TextUtils.isEmpty(tags)) add.add("tagall", tags);
        mPresenter.getData(ApiConfig.GROUP_DETAIL_FOOTER_DATA, pNormal, add);
    }

    private void setDetailData(GroupDetailEntity groupInfo) {
        GroupDetailEntity.Group groupInner = groupInfo.getGroupinfo();
        tvName.setText(groupInner.getGroup_name());
        groupTitle.setText(groupInner.getGroup_name());
        tvMemberNum.setText("成员 " + groupInner.getMember_num() + " 人");
        tvPostNum.setText("资料 " + groupInner.getThread_num() + " 篇");
        tvFocus.setText(groupInner.getIs_add() == 1 ? "已关注" : "关注");
        GlideUtil.loadCornerImage(ivThumb, groupInner.getLogo(), null, 10);
        GlideUtil.loadBlurredBackground(groupInner.getLogo(), imageBack);
        mTabListData.addAll(groupInfo.getTag_arr());
        mGroupDetailCenterTabAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.groupBack)
    public void onViewClicked() {
        mActivity.mProjectController.navigateUp();
    }

    private int page = 1;

    @Override
    public void dataType(int mode) {
        page = mode == LoadTypeConfig.REFRESH ? 1 : page+1;
        getFooterData(mode);
    }
}
