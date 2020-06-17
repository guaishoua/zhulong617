package com.hdj.fragment;

import android.util.Log;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hdj.adater.CourseAdapter;
import com.hdj.base.Application1907;
import com.hdj.base.BaseMvpFragment;
import com.hdj.data.BaseInfo;
import com.hdj.data.CourseBean;
import com.hdj.frame.ApiConfig;
import com.hdj.frame.LoadTypeConfig;
import com.hdj.interfaces.DataListener;
import com.hdj.model.CourseModel;
import com.hdj.util.ParamHashMap;
import com.hdj.zhulong.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;


public class CourseChildFragment extends BaseMvpFragment<CourseModel> {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    private CourseAdapter mCourseAdapter;

    private int page = 1;
    private int course_type;
    private String specialty_id;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_jing_pin;
    }

    @Override
    public CourseModel setModel() {
        return new CourseModel();
    }

    @Override
    public void setUpView() {
        if(getArguments()!=null){
            course_type = getArguments().getInt("course_type");
        }
        specialty_id = Application1907.getFrameApplication().getSelectedInfo().getSpecialty_id();
        Log.d("TAG", "setUpView: "+specialty_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mCourseAdapter = new CourseAdapter(mContext);
        recyclerView.setAdapter(mCourseAdapter);
    }

    @Override
    public void setUpData() {
        ParamHashMap paramHashMap = new ParamHashMap().add("specialty_id", specialty_id).add("page", page).add("limit", 15).add("course_type", course_type);
        mPresenter.getData(ApiConfig.GET_COURSE_DATA, paramHashMap, LoadTypeConfig.NORMAL);
        initRecyclerView(recyclerView, smartRefresh, new DataListener() {
            @Override
            public void dataType(int mode) {
                if (mode == LoadTypeConfig.MORE) {
                    page++;
                    ParamHashMap paramHashMap = new ParamHashMap().add("specialty_id", specialty_id).add("page", page).add("limit", 15).add("course_type", course_type);
                    mPresenter.getData(ApiConfig.GET_COURSE_DATA, paramHashMap, LoadTypeConfig.MORE);
                } else if (mode == LoadTypeConfig.REFRESH) {
                    page = 1;
                    ParamHashMap paramHashMap = new ParamHashMap().add("specialty_id", specialty_id).add("page", page).add("limit", 15).add("course_type", course_type);
                    mPresenter.getData(ApiConfig.GET_COURSE_DATA, paramHashMap, LoadTypeConfig.REFRESH);
                }
            }
        });
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.GET_COURSE_DATA:
                BaseInfo<CourseBean> courseBeanBaseInfo = (BaseInfo<CourseBean>) pD[0];
                Integer[] integers = (Integer[]) pD[1];
                int loadType = integers[0];
                CourseBean result = courseBeanBaseInfo.result;
                Log.d("TAG", "netSuccess: "+loadType);
                List<CourseBean.ListsBean> lists = result.getLists();
                if (loadType == LoadTypeConfig.MORE) {
                    smartRefresh.finishLoadMore();
                    mCourseAdapter.setListsBeans(lists);
                } else if (loadType == LoadTypeConfig.REFRESH) {
                    smartRefresh.finishRefresh();
                    mCourseAdapter.setReListsBeans(lists);
                } else {
                    mCourseAdapter.setListsBeans(lists);
                }
                break;
        }
    }
}
