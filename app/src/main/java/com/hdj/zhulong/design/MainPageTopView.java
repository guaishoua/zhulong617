package com.hdj.zhulong.design;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hdj.adater.MainliveAdapter;
import com.hdj.data.HomeBannerBean;
import com.hdj.frame.ApiService;
import com.hdj.frame.NetManger;
import com.hdj.util.ParamHashMap;
import com.hdj.zhulong.R;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPageTopView extends RelativeLayout {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private Context mContext;
    private MainliveAdapter mMainliveAdapter;
    List<HomeBannerBean.ResultBean.CarouselBean> mCarousel = new ArrayList<>();

    public MainPageTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.main_page_top_view, this);
        initView();
        initData();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        mMainliveAdapter = new MainliveAdapter(mContext);
        recyclerView.setAdapter(mMainliveAdapter);
        if (mCarousel != null && mCarousel.size() != 0) {
            banner.setImages(mCarousel).setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    HomeBannerBean.ResultBean.CarouselBean carouselBean = (HomeBannerBean.ResultBean.CarouselBean) path;
                    Glide.with(context).load(carouselBean.getImg()).into(imageView);
                }
            }).start();
        }
    }

    private void initData() {//more_live=1&is_new=1&new_banner=1


    }
}
