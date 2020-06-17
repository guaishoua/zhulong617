package com.hdj.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hdj.base.BaseMvpFragment;
import com.hdj.frame.IContract;
import com.hdj.zhulong.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class CourseFragment extends BaseMvpFragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_course;
    }

    @Override
    public IContract.IModel setModel() {
        return null;
    }

    @Override
    public void setUpView() {
        List<Fragment> fragments = new ArrayList<>();
        int [] isChe={3,1,2};
        for (int i = 0; i < isChe.length; i++) {
            CourseChildFragment courseChildFragment = new CourseChildFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("course_type",isChe[i]);
            courseChildFragment.setArguments(bundle);
            fragments.add(courseChildFragment);
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
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
        tabLayout.getTabAt(0).setText("训练营");
        tabLayout.getTabAt(1).setText("精品课");
        tabLayout.getTabAt(2).setText("公开课");
    }

    @Override
    public void setUpData() {

    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {

    }
}
