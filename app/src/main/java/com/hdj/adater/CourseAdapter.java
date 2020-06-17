package com.hdj.adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hdj.data.CourseBean;
import com.hdj.utils.newAdd.GlideUtil;
import com.hdj.zhulong.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private Context mContext;
    private List<CourseBean.ListsBean> mListsBeans = new ArrayList<>();

    public CourseAdapter(Context pContext) {
        mContext = pContext;
    }

    public void setListsBeans(List<CourseBean.ListsBean> pListsBeans) {
        mListsBeans.addAll(pListsBeans);
        notifyDataSetChanged();
    }

    public void setReListsBeans(List<CourseBean.ListsBean> pListsBeans) {
        mListsBeans.clear();
        mListsBeans.addAll(pListsBeans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_course, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        CourseBean.ListsBean listsBean = mListsBeans.get(position);
        GlideUtil.loadImage(viewHolder.ivImg, listsBean.getThumb());
        viewHolder.tvTitle.setText(listsBean.getLesson_name());
        viewHolder.tvLike.setText("好评率"+listsBean.getRate());
        viewHolder.tvPrice.setText("￥" + listsBean.getPrice());
        viewHolder.tvPeopleCount.setText(listsBean.getStudentnum()+"人学习");
    }

    @Override
    public int getItemCount() {
        return mListsBeans.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_people_count)
        TextView tvPeopleCount;
        @BindView(R.id.tv_like)
        TextView tvLike;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
