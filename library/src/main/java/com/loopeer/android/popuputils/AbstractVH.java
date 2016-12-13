package com.loopeer.android.popuputils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class AbstractVH<T> extends RecyclerView.ViewHolder{
    public AbstractVH(View itemView) {
        super(itemView);
    }

    public abstract void bind(T item);
}
