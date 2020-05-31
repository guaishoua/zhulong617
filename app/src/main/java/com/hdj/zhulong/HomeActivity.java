package com.hdj.zhulong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hdj.base.BaseActivity;
import com.hdj.base.BaseMvpActivity;
import com.hdj.fragment.CourseFragment;
import com.hdj.fragment.DataFragment;
import com.hdj.fragment.HomeFragment;
import com.hdj.fragment.MineFragment;
import com.hdj.fragment.VipFragment;
import com.hdj.frame.ApiConfig;
import com.hdj.frame.MyModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends BaseMvpActivity {

    private static final int HOME = 0;
    private static final int COURES = 1;
    private static final int VIP = 2;
    private static final int DATA = 3;
    private static final int MINE = 4;
    private FrameLayout frameLayout;
    private TabLayout tabLayout;
    private Fragment lastFragment;
    private HomeFragment homeFragment;
    private CourseFragment courseFragment;
    private VipFragment vipFragment;
    private DataFragment dataFragment;
    private MineFragment mineFragment;
    private int tabType;
    private FragmentManager supportFragmentManager;

    @Override
    protected MyModel initModel() {
        return new MyModel();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);


        supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        courseFragment = new CourseFragment();
        vipFragment = new VipFragment();
        dataFragment = new DataFragment();
        mineFragment = new MineFragment();
        lastFragment = homeFragment;

        fragmentTransaction.add(R.id.frameLayout, homeFragment)
                .add(R.id.frameLayout, courseFragment)
                .add(R.id.frameLayout, vipFragment)
                .add(R.id.frameLayout, dataFragment)
                .add(R.id.frameLayout, mineFragment)
                .show(homeFragment)
                .hide(courseFragment)
                .hide(vipFragment)
                .hide(dataFragment)
                .hide(mineFragment)
                .commit();


        tabLayout.addTab(tabLayout.newTab(),0);
        tabLayout.addTab(tabLayout.newTab(),1);
        tabLayout.addTab(tabLayout.newTab(),2);
        tabLayout.addTab(tabLayout.newTab(),3);
        tabLayout.addTab(tabLayout.newTab(),4);
        tabLayout.getTabAt(0).setCustomView(getView("主页", R.drawable.home_selector));
        tabLayout.getTabAt(1).setCustomView(getView("课程", R.drawable.course_selector));
        tabLayout.getTabAt(2).setCustomView(getView("VIP", R.drawable.vip_selector));
        tabLayout.getTabAt(3).setCustomView(getView("资料", R.drawable.data_selector));
        tabLayout.getTabAt(4).setCustomView(getView("我的", R.drawable.mine_selector));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tabType = HOME;
                        break;
                    case 1:
                        tabType = COURES;
                        break;
                    case 2:
                        tabType = VIP;
                        break;
                    case 3:
                        tabType = DATA;
                        break;
                    case 4:
                        tabType = MINE;
                        break;
                }
                setFragment();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public Fragment switchFragment() {
        switch (tabType) {
            case HOME:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                return homeFragment;
            case COURES:
                if (courseFragment == null) {
                    courseFragment = new CourseFragment();
                }
                return courseFragment;
            case VIP:
                if (vipFragment == null) {
                    vipFragment = new VipFragment();
                }
                return vipFragment;
            case DATA:
                if (dataFragment == null) {
                    dataFragment = new DataFragment();
                }
                return dataFragment;
            case MINE:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                }
                return mineFragment;
        }
        return null;
    }

    public void setFragment() {
        Fragment currFragment = switchFragment();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.show(currFragment)
                .hide(lastFragment)
                .commit();
        lastFragment = currFragment;
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

    public View getView(String title, int image) {
        View root = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
        ImageView iv_img = root.findViewById(R.id.iv_img);
        TextView tv_title = root.findViewById(R.id.tv_title);
        iv_img.setImageResource(image);
        tv_title.setText(title);
        return root;
    }
}
