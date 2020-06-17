package com.hdj.adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.hdj.data.GroupDetailEntity;
import com.hdj.zhulong.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GroupDetailCenterTabAdapter extends RecyclerView.Adapter<GroupDetailCenterTabAdapter.ViewHolder> {

    private Context mContext;
    private List<GroupDetailEntity.Tag> mTabListData;

    public GroupDetailCenterTabAdapter(Context pContext, List<GroupDetailEntity.Tag> pTabListData) {
        mContext = pContext;
        mTabListData = pTabListData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.group_detail_tab_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tagContent.setText(mTabListData.get(position).getTag_name());
        holder.fallsView.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mTabListData != null ? mTabListData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.falls_view)
        View fallsView;
        @BindView(R.id.tagContent)
        TextView tagContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
