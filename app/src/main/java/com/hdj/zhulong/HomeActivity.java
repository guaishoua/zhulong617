package com.hdj.zhulong;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayout;
import com.hdj.base.BaseMvpActivity;
import com.hdj.fragment.CourseFragment;
import com.hdj.fragment.DataFragment;
import com.hdj.fragment.HomeFragment;
import com.hdj.fragment.MineFragment;
import com.hdj.fragment.VipFragment;
import com.hdj.model.MyModel;

public class HomeActivity extends BaseMvpActivity {
    public NavController mProjectController;
    @Override
    protected MyModel initModel() {
        return new MyModel();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mProjectController = Navigation.findNavController(this, R.id.project_fragment_control);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void netSuccess(int whichApi, Object[] params) {

    }

    @Override
    protected void netFailed(int whichApi, Throwable throwable) {

    }

}
