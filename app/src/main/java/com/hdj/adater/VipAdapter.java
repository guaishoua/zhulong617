package com.hdj.adater;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hdj.data.HomeBannerBean;
import com.hdj.data.HomeliveBean;
import com.hdj.data.VipBannerBean;
import com.hdj.data.VipListBean;
import com.hdj.utils.ext.StringUtils;
import com.hdj.zhulong.R;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VipAdapter extends RecyclerView.Adapter<VipAdapter.ViewHolder> {
    private Context mContext;
    private List<VipBannerBean.ResultBean.LunbotuBean> banners = new ArrayList<>();
    private List<VipBannerBean.ResultBean.LiveBeanX.LiveBean> liveBeans = new ArrayList<>();
    private List<VipListBean.ListBean> list = new ArrayList<>();
    private final int BANNER = 1, LIVE = 2, LIST = 3;

    public VipAdapter(Context pContext) {
        mContext = pContext;
    }

    public void setBanners(List<VipBannerBean.ResultBean.LunbotuBean> pBanners) {
        this.banners.addAll(pBanners);
        notifyDataSetChanged();
    }

    public void setLiveingEntitys(List<VipBannerBean.ResultBean.LiveBeanX.LiveBean> liveBeans) {
        this.liveBeans.addAll(liveBeans);
        notifyDataSetChanged();
    }

    public void setList(List<VipListBean.ListBean> pList) {
        list.addAll(pList);
        notifyDataSetChanged();
    }

    public void setReList(List<VipListBean.ListBean> pList) {
        this.list.clear();
        list.addAll(pList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        int type = LIST;
        if (position == 0) type = BANNER;
        else if (liveBeans != null && liveBeans.size() != 0 && position == 1) type = LIVE;
        else {
            if (list != null && list.size() != 0) type = LIST;
        }
        return type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @LayoutRes int layoutId = 0;
        if (viewType == BANNER) {
            layoutId = R.layout.item_homepage_ad;
        } else if (viewType == LIVE) {//recleview，
            layoutId = R.layout.live_recycler_item;
        } else if (viewType == LIST) {//大图
            layoutId = R.layout.item_vip;
        }
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            if (banners.size() != 0) {
                holder.banner.setImages(banners).setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        VipBannerBean.ResultBean.LunbotuBean path1 = (VipBannerBean.ResultBean.LunbotuBean) path;
                        Glide.with(context).load(path1.getImg()).into(imageView);
                    }
                }).start();
            }
        } else if (getItemViewType(position) == LIVE) {
            if (liveBeans != null && liveBeans.size() != 0) {
                LinearLayoutManager manager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
                holder.recyclerView.setLayoutManager(manager);
                holder.recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
                VipliveAdapter adapter = new VipliveAdapter(mContext);
                adapter.setData(liveBeans);
                holder.recyclerView.setAdapter(adapter);
            } else {
                holder.hindView();
            }

        } else {
            if (list.size() == 0) return;
            VipListBean.ListBean listBean = list.get(liveBeans == null || liveBeans.size() == 0 ? position - 1 : position - 2);
            holder.tvVipCourseTitle.setText(listBean.getLesson_name());
            holder.tvVipCourseCount.setText( listBean.getStudentnum()+"人学习");
            holder.tvVipCourseRate.setText(listBean.getComment_rate()+"好评");
            Glide.with(mContext).load(listBean.getVip_thumb()).into(holder.ivVipCourseThumb);
        }
    }


    @Override
    public int getItemCount() {
        return liveBeans != null && liveBeans.size() != 0 ? list.size() + 2 : list.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout liveView;
        View views;
        Banner banner;
        RecyclerView recyclerView;

        ImageView ivVipCourseThumb;
        TextView tvVipCourseTitle;
        TextView tvVipCourseCount;
        TextView tvVipCourseRate;
        public void hindView() {
            liveView.setVisibility(View.GONE);
            views.setVisibility(View.GONE);
        }

        public ViewHolder(@NonNull View itemView, int type) {
            super(itemView);
            if (type == BANNER) {
                banner = itemView.findViewById(R.id.banner);
            } else if (type == LIVE) {
                recyclerView = itemView.findViewById(R.id.recyclerView);
                liveView = itemView.findViewById(R.id.live_view);
                views = itemView.findViewById(R.id.views);
            } else if(type == LIST){
                tvVipCourseTitle = itemView.findViewById(R.id.tv_vip_course_title);
                tvVipCourseCount = itemView.findViewById(R.id.tv_vip_course_count);
                tvVipCourseRate = itemView.findViewById(R.id.tv_vip_course_rate);
                ivVipCourseThumb = itemView.findViewById(R.id.iv_vip_course_thumb);
            }
        }
    }
}
