package com.hdj.zhulong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayout;
import com.hdj.base.BaseMvpActivity;
import com.hdj.fragment.CourseFragment;
import com.hdj.fragment.DataFragment;
import com.hdj.fragment.HomeFragment;
import com.hdj.fragment.MineFragment;
import com.hdj.fragment.VipFragment;
import com.hdj.model.MyModel;

public class HomeActivity extends BaseMvpActivity implements NavController.OnDestinationChangedListener {
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
        mProjectController.addOnDestinationChangedListener(this);
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

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        String label = destination.getLabel().toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

