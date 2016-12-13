package com.loopeer.android.popuputils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter {

    private List<T> mData;
    private T mCurItem;

    private RecyclerView.ViewHolder mPreViewHolder;
    private RecyclerView.ViewHolder mCurViewHolder;

    private OnItemClickListener<T> mOnItemClickListener;

    public RecyclerAdapter(List<T> data) {
        mData = new ArrayList<>();
        mData.addAll(data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final T data = mData.get(position);
        ((AbstractVH<T>) holder).bind(data);

        if (mOnItemClickListener != null) {
            if (mCurItem != data) {
                mOnItemClickListener.setItemStyle(holder, null);
            } else {
                mCurViewHolder = holder;
                mOnItemClickListener.setItemStyle(null, holder);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurItem = data;
                mPreViewHolder = mCurViewHolder;
                mCurViewHolder = holder;


                if (mOnItemClickListener != null) {
                    mOnItemClickListener.setItemStyle(mPreViewHolder, mCurViewHolder);
                    mOnItemClickListener.onItemClick(data, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public T getCurItem() {
        return mCurItem;
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void updateData(List<T> data) {
        if (data == null) return;

        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public abstract AbstractVH<T> createViewHolder(ViewGroup parent);

    public static abstract class OnItemClickListener<T> {
        public abstract void onItemClick(T data, int position);

        public void setItemStyle(RecyclerView.ViewHolder preItemViewHolder, RecyclerView.ViewHolder curItemViewHolder) {
        }
    }
}
