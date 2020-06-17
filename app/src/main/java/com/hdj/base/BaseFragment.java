package com.hdj.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hdj.data.SpecialtyChooseEntity;
import com.hdj.frame.LoadTypeConfig;
import com.hdj.interfaces.DataListener;
import com.hdj.utils.newAdd.SharedPrefrenceUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public abstract class BaseFragment extends Fragment {




    public void initRecyclerView(RecyclerView pRecyclerView, SmartRefreshLayout pRefreshLayout, DataListener pDataListener) {
        if (pRecyclerView != null)
//        pRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (pRefreshLayout != null && pDataListener != null) {
            pRefreshLayout.setOnRefreshListener(refreshLayout -> pDataListener.dataType(LoadTypeConfig.REFRESH));
            pRefreshLayout.setOnLoadMoreListener(refreshLayout -> pDataListener.dataType(LoadTypeConfig.MORE));
        }
    }


    public int setColor(@ColorRes int pColor){
        return ContextCompat.getColor(getContext(),pColor);
    }

    public void showLog(Object content) {
        Log.e("睚眦", content.toString());
    }

    public void showToast(Object content) {
        Toast.makeText(getContext(), content.toString(), Toast.LENGTH_SHORT).show();
    }
}
