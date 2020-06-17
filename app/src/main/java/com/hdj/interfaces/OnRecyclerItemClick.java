package com.hdj.interfaces;

public interface OnRecyclerItemClick<T> {
    void onItemClick(int pos,T... pTS);
}
