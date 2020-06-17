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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hdj.data.HomeBannerBean;
import com.hdj.data.HomeliveBean;
import com.hdj.utils.ext.StringUtils;
import com.hdj.utils.newAdd.GlideUtil;
import com.hdj.zhulong.R;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPageAdapter extends RecyclerView.Adapter<MainPageAdapter.ViewHolder> {
    private Context mContext;
    private List<HomeBannerBean.ResultBean.CarouselBean> banners = new ArrayList<>();
    private List<HomeBannerBean.ResultBean.LiveBean> liveingEntitys = new ArrayList<>();
    private List<HomeliveBean> list = new ArrayList<>();
    private final int BANNER = 1, LABEL = 2, LIVE = 3, RIGHT_IMAGE = 4, BIG_IMAGE = 5;

    public MainPageAdapter(Context pContext) {
        mContext = pContext;
    }

    public void setBanners(List<HomeBannerBean.ResultBean.CarouselBean> pBanners) {
        banners.addAll(pBanners);
        notifyDataSetChanged();
    }

    public void setLiveingEntitys(List<HomeBannerBean.ResultBean.LiveBean> pLiveingEntitys) {
        liveingEntitys.addAll(pLiveingEntitys);
        notifyDataSetChanged();
    }

    public void setList(List<HomeliveBean> pList) {
        list.addAll(pList);
        notifyDataSetChanged();
    }

    public void setReList(List<HomeliveBean> pList) {
        this.list.clear();
        list.addAll(pList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        int type = RIGHT_IMAGE;
        if (position == 0) type = BANNER;
        else if (position == 1) type = LABEL;
        else if (liveingEntitys != null && liveingEntitys.size() != 0 && position == 2) type = LIVE;
        else {
            int usePos = liveingEntitys != null && liveingEntitys.size() != 0 ? position - 3 : position - 2;
            if (list != null && list.size() != 0) {
                if (list.get(usePos).getType() == 3) {
                    type = RIGHT_IMAGE;
                } else type = BIG_IMAGE;
            }
        }

        return type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @LayoutRes int layoutId;
        if (viewType == BANNER) {
            layoutId = R.layout.item_homepage_ad;
        } else if (viewType == LABEL) {
            layoutId = R.layout.item_homepage_shortcuts;
        } else if (viewType == LIVE) {//recleview，
            layoutId = R.layout.live_recycler_item;
        } else if (viewType == BIG_IMAGE) {//大图
            layoutId = R.layout.item_list_mianpage_two;
        } else {
            layoutId = R.layout.item_list_mianpage_one;
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
                        HomeBannerBean.ResultBean.CarouselBean path1 = (HomeBannerBean.ResultBean.CarouselBean) path;
                        Glide.with(context).load(path1.getImg()).into(imageView);
                    }
                }).start();
            }
        } else if (getItemViewType(position) == LIVE) {
            if (liveingEntitys != null && liveingEntitys.size() != 0) {
                LinearLayoutManager manager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
                holder.tv_title.setText("直播专区");
                holder.recyclerView.setLayoutManager(manager);
                holder.recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
                MainliveAdapter adapter = new MainliveAdapter(mContext);
                adapter.setData(liveingEntitys);
                holder.recyclerView.setAdapter(adapter);
            } else {
                holder.hindView();
            }

        } else if (getItemViewType(position) == LABEL) {

        } else {
            if (list.size() == 0) return;
            HomeliveBean homeliveBean = list.get(liveingEntitys == null || liveingEntitys.size() == 0 ? position - 2 : position - 3);
            if (getItemViewType(position) == BIG_IMAGE) {
                holder.tvTitle.setText(homeliveBean.getTitle());
                Glide.with(mContext).load(homeliveBean.getPic()).into(holder.image);
                holder.studyNum.setText(homeliveBean.getView_num()+"人学习");
                holder.goodPercent.setText(homeliveBean.getRate()+"人浏览");
            } else {
                holder.title.setText(StringUtils.translation(homeliveBean.getTitle()));
                Glide.with(mContext).load(homeliveBean.getPic()).into(holder.ivPhoto);
                holder.tvAuthorAndTime.setText(homeliveBean.getDate());
                holder.tvCommentNum.setText((TextUtils.isEmpty(homeliveBean.getReply_num()) ? 0 : homeliveBean.getReply_num()) + "人跟帖");
                holder.tvBrowseNum.setText(homeliveBean.getView_num() + "人浏览");
            }
        }
    }



    @Override
    public int getItemCount() {
        return liveingEntitys != null && liveingEntitys.size() != 0 ? list.size() + 3 : list.size() + 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout liveView;
        View views;
        Banner banner;

        RecyclerView recyclerView;
        TextView tv_title;
        TextView tvTitle;
        ImageView image;
        TextView studyNum;
        TextView goodPercent;

        TextView title;
        TextView tvBrowseNum;
        TextView tvCommentNum;
        ImageView ivPhoto;
        TextView tvAuthorAndTime;

        public void hindView(){
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
                tv_title = itemView.findViewById(R.id.tv_title);
                views = itemView.findViewById(R.id.views);
            } else if (type == BIG_IMAGE) {
                tvTitle = itemView.findViewById(R.id.tv_title);
                image = itemView.findViewById(R.id.iv_photo);
                studyNum = itemView.findViewById(R.id.tv_learn_num);
                goodPercent = itemView.findViewById(R.id.tv_like_num);
            } else if (type == RIGHT_IMAGE) {
                title = itemView.findViewById(R.id.list_one_title);
                tvBrowseNum = itemView.findViewById(R.id.tv_browse_num);
                tvCommentNum = itemView.findViewById(R.id.tv_comment_num);
                ivPhoto = itemView.findViewById(R.id.iv_photo);
                tvAuthorAndTime = itemView.findViewById(R.id.tv_author_and_time);
            }
        }
    }
}
