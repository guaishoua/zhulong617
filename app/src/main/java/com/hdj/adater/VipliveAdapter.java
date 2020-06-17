package com.hdj.adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hdj.data.HomeBannerBean;
import com.hdj.data.VipBannerBean;
import com.hdj.utils.newAdd.TimeUtil;
import com.hdj.zhulong.R;
import com.hdj.zhulong.design.RoundImage;
import com.hdj.zhulong.utils.ZLImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VipliveAdapter extends RecyclerView.Adapter<VipliveAdapter.ViewHolder> {
    private Context mContext;
    private List<VipBannerBean.ResultBean.LiveBeanX.LiveBean> data = new ArrayList<>();

    public VipliveAdapter(Context pContext) {
        mContext = pContext;
    }

    public void setData(List<VipBannerBean.ResultBean.LiveBeanX.LiveBean> pData) {
        data.addAll(pData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_live, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VipBannerBean.ResultBean.LiveBeanX.LiveBean liveBean = data.get(position);
        holder.tvLiveName.setText(liveBean.getActivityName());

        ZLImageLoader.getInstance().displayImage(mContext, liveBean.getTeacher_photo(),holder.ivTeacherAvatar);
        int is_liveing = liveBean.getIs_liveing();
        if(is_liveing==0){
            holder.llLiving.setVisibility(View.GONE);
            holder.llLivePre.setVisibility(View.VISIBLE);
            holder.tvLiveTime.setText(TimeUtil.formatLiveTime(Long.valueOf(liveBean.getStartTime())));

        }else{
            holder.llLiving.setVisibility(View.VISIBLE);
            holder.llLivePre.setVisibility(View.GONE);
            ZLImageLoader.getInstance().displayImage(mContext,
                    ZLImageLoader.getUriFromResource(mContext, R.drawable.play), holder.ivLiving);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_teacher_avatar)
        RoundImage ivTeacherAvatar;
        @BindView(R.id.tv_live_name)
        TextView tvLiveName;
        @BindView(R.id.iv_living)
        ImageView ivLiving;
        @BindView(R.id.ll_living)
        LinearLayout llLiving;
        @BindView(R.id.tv_live_time)
        TextView tvLiveTime;
        @BindView(R.id.ll_live_pre)
        LinearLayout llLivePre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
