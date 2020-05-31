package com.hdj.zhulong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hdj.base.BaseActivity;
import com.hdj.fragment.CourseFragment;
import com.hdj.fragment.DataFragment;
import com.hdj.fragment.HomeFragment;
import com.hdj.fragment.MineFragment;
import com.hdj.fragment.VipFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CourseFragment());
        fragments.add(new VipFragment());
        fragments.add(new DataFragment());
        fragments.add(new MineFragment());
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setCustomView(getView("主页",R.drawable.home_selector));
        tabLayout.getTabAt(1).setCustomView(getView("课程",R.drawable.course_selector));
        tabLayout.getTabAt(2).setCustomView(getView("VIP",R.drawable.vip_selector));
        tabLayout.getTabAt(3).setCustomView(getView("资料",R.drawable.data_selector));
        tabLayout.getTabAt(4).setCustomView(getView("我的",R.drawable.mine_selector));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }
    public View getView(String title,int image){
        View root = LayoutInflater.from(this).inflate(R.layout.tab_item,null);
        ImageView iv_img = root.findViewById(R.id.iv_img);
        TextView tv_title = root.findViewById(R.id.tv_title);
        iv_img.setImageResource(image);
        tv_title.setText(title);
        return root;
    }
}
