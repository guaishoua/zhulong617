package com.hdj.adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hdj.data.HomeliveBean;
import com.hdj.zhulong.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<HomeliveBean> data = new ArrayList();
    private final int ONE_TYPE = 0;
    private final int TWO_TYPE = 1;

    public MainListAdapter(Context pContext) {
        context = pContext;
    }

    public void addList(List<HomeliveBean> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void refresh(List<HomeliveBean> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        int type = data.get(position).getType();
        if (type == 3) {
            return ONE_TYPE;
        } else {
            return TWO_TYPE;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ONE_TYPE) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.item_list_mianpage_one, parent, false);
            return new OneViewHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(context).inflate(R.layout.item_list_mianpage_two, parent, false);
            return new TwoViewHolder(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        HomeliveBean liveListBean = data.get(position);
        if (itemViewType == ONE_TYPE) {
            OneViewHolder holder1 = (OneViewHolder) holder;
            holder1.listOneTitle.setText(liveListBean.getTitle());
            holder1.tvAuthorAndTime.setText(liveListBean.getDate());
            holder1.tvCommentNum.setText(liveListBean.getReply_num() + "人跟帖");
            holder1.tvBrowseNum.setText(liveListBean.getView_num() + "人浏览");
            Glide.with(context).load(liveListBean.getPic()).into(holder1.ivPhoto);
          /*  indexCommondtizi.getPic()
                    .setText(R.id.tv_comment_num, String.format("%s人跟帖", ))
                    .setVisible(R.id.tv_comment_num, !"0".equals(liveListBean.getReply_num()))
                    .setText(R.id.tv_browse_num, String.format("%s人浏览", liveListBean.getView_num()))
                    .setVisible(R.id.tv_browse_num, !"0".equals(liveListBean.getView_num()));*/
        } else {
            TwoViewHolder holder1 = (TwoViewHolder) holder;
            Glide.with(context).load(liveListBean.getPic()).into(holder1.ivPhoto);
            holder1.tvTitle.setText(liveListBean.getTitle());
            holder1.tvLikeNum.setText(liveListBean.getRate() + "好评率");
            holder1.tvLearnNum.setText(liveListBean.getView_num() + "人学习");
            /*learnPeopleCount.append(String.format("%1s", StringUtils.getPeople(indexCommondclss.getView_num()))).append("学习");
            helper.setText(R.id.tv_learn_num, learnPeopleCount.toString());*/

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class OneViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_one_title)
        TextView listOneTitle;
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.tv_browse_num)
        TextView tvBrowseNum;
        @BindView(R.id.tv_comment_num)
        TextView tvCommentNum;
        @BindView(R.id.tv_author_and_time)
        TextView tvAuthorAndTime;

        public OneViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class TwoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.tv_learn_num)
        TextView tvLearnNum;
        @BindView(R.id.tv_like_num)
        TextView tvLikeNum;


        public TwoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
