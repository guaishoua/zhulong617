package com.hdj.adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hdj.data.VipListBean;
import com.hdj.zhulong.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VipListAdapter extends RecyclerView.Adapter<VipListAdapter.ViewHolder> {
    private Context mContext;
    private List<VipListBean.ListBean> mListBeans = new ArrayList<>();

    public VipListAdapter(Context pContext) {
        mContext = pContext;
    }

    public void setListBeans(List<VipListBean.ListBean> pListBeans) {
        mListBeans.addAll(pListBeans);
        notifyDataSetChanged();
    }


    public void setReListBeans(List<VipListBean.ListBean> pListBeans) {
        this.mListBeans.clear();
        mListBeans.addAll(pListBeans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VipListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_vip,parent,false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull VipListAdapter.ViewHolder holder, int position) {
        VipListBean.ListBean listBean = mListBeans.get(position);
        holder.tvVipCourseTitle.setText(listBean.getLesson_name());

        holder.tvVipCourseCount.setText( listBean.getStudentnum()+"人学习");
        holder.tvVipCourseRate.setText(listBean.getComment_rate()+"好评");
        Glide.with(mContext).load(listBean.getVip_thumb()).into(holder.ivVipCourseThumb);
    }

    @Override
    public int getItemCount() {
        return mListBeans.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_vip_course_thumb)
        ImageView ivVipCourseThumb;
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.tv_vip_course_title)
        TextView tvVipCourseTitle;
        @BindView(R.id.tv_vip_course_author)
        TextView tvVipCourseAuthor;
        @BindView(R.id.tv_vip_course_count)
        TextView tvVipCourseCount;
        @BindView(R.id.tv_vip_course_rate)
        TextView tvVipCourseRate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
