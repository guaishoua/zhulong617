package com.hdj.adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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

public class DataGroupAdapter extends RecyclerView.Adapter<DataGroupAdapter.ViewHolder> {

    public List<DataGroupBean> mList = new ArrayList<>();
    private Context mContext;
    public static final int ITEM_TYPE = 1, FOCUS_TYPE = 2;

    public DataGroupAdapter(Context pContext) {
        mContext = pContext;
    }

    public void setList(List<DataGroupBean> pList) {
        mList.addAll(pList);
        notifyDataSetChanged();
    }

    public void setReList(List<DataGroupBean> pList) {
        this.mList.clear();
        mList.addAll(pList);
        notifyDataSetChanged();
    }

    public DataGroupBean getDataGroupBean(int pos){
        return mList.get(pos);
    }

    public List<DataGroupBean> getList() {
        return mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_group_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataGroupBean dataGroupBean = mList.get(position);

        Glide.with(mContext).load(dataGroupBean.getAvatar()).into(holder.dataImg);
        holder.dataTitle.setText(dataGroupBean.getGroup_name());
        holder.dataCourseCount.setText(dataGroupBean.getMember_num()+"关注");
        holder.dataDesc.setText(dataGroupBean.getIntroduce());
        holder.tvFocus.setText(dataGroupBean.isFocus() ? "已关注" : "+关注");
        holder.tvFocus.setSelected(dataGroupBean.isFocus() ? true : false);
        holder.tvFocus.setTextColor(dataGroupBean.isFocus() ? ContextCompat.getColor(mContext,R.color.red) : ContextCompat.getColor(mContext,R.color.fontColorGray));
        holder.tvFocus.setOnClickListener(view->{
            if (mOnRecyclerItemClick !=null)mOnRecyclerItemClick.onItemClick(position,FOCUS_TYPE);
        });
        holder.itemView.setOnClickListener(view->{
            if(mOnRecyclerItemClick != null)mOnRecyclerItemClick.onItemClick(position,ITEM_TYPE);
        });
    }

    private OnRecyclerItemClick mOnRecyclerItemClick;

    public void setOnRecyclerItemClick(OnRecyclerItemClick pOnRecyclerItemClick){
        mOnRecyclerItemClick = pOnRecyclerItemClick;
    }


    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.data_img)
        ImageView dataImg;
        @BindView(R.id.data_title)
        TextView dataTitle;
        @BindView(R.id.data_course_count)
        TextView dataCourseCount;
        @BindView(R.id.data_desc)
        TextView dataDesc;
        @BindView(R.id.tv_focus)
        TextView tvFocus;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
