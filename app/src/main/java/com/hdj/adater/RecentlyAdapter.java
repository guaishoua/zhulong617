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
import com.hdj.data.DataGroupBean;
import com.hdj.data.RecentlyBean;
import com.hdj.interfaces.OnRecyclerItemClick;
import com.hdj.utils.newAdd.GlideUtil;
import com.hdj.utils.newAdd.TimeUtil;
import com.hdj.zhulong.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecentlyAdapter extends RecyclerView.Adapter<RecentlyAdapter.ViewHolder> {
    private List<RecentlyBean> data  = new ArrayList<>();
    private Context context;

    public RecentlyAdapter(Context pContext) {
        context = pContext;
    }

    public void setData(List<RecentlyBean> pData) {
        data.addAll(pData);
        notifyDataSetChanged();
    }
    public void setReData(List<RecentlyBean> pData) {
        this.data.clear();
        data.addAll(pData);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_mianpage_one, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecentlyBean recentlyBean = data.get(position);
        Glide.with(context).load(recentlyBean.getPic()).into(holder.ivPhoto);
        holder.listOneTitle.setText(recentlyBean.getTitle());
        holder.tvBrowseNum.setText(recentlyBean.getView_num()+"人浏览");
        holder.tvCommentNum.setText(recentlyBean.getReply_num()+"人跟帖");
        holder.tvAuthorAndTime.setText(TimeUtil.formatLiveTime(Long.valueOf(recentlyBean.getCreate_time())));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_one_title)
        TextView listOneTitle;
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.cv_photo)
        CardView cvPhoto;
        @BindView(R.id.tv_browse_num)
        TextView tvBrowseNum;
        @BindView(R.id.tv_comment_num)
        TextView tvCommentNum;
        @BindView(R.id.tv_author_and_time)
        TextView tvAuthorAndTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnRecyclerItemClick mOnRecyclerItemClick;

    public void setOnRecyclerItemClick(OnRecyclerItemClick pOnRecyclerItemClick){
        mOnRecyclerItemClick = pOnRecyclerItemClick;
    }
}
