package com.loopeer.android.popuputils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.List;

public class TwoLayersPopup<P, S> extends BasePopup {
    protected RecyclerView mPrimaryLayer;
    protected RecyclerView mSecondaryLayer;

    protected List<P> mPrimaryData;
    protected List<S> mSecondaryData;

    protected RecyclerAdapter<P> mPrimaryAdapter;
    protected RecyclerAdapter<S> mSecondaryAdapter;
    protected TwoLayersAdapter<P, S> mTwoLayersAdapter;

    public TwoLayersPopup(Context context) {
        super(context);
    }

    public void setLayerWeight(int primaryLayerWeight, int secondaryLayerWeight) {
        LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) mPrimaryLayer.getLayoutParams();
        lp1.weight = primaryLayerWeight;
        mPrimaryLayer.setLayoutParams(lp1);
        mPrimaryLayer.requestLayout();

        LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) mSecondaryLayer.getLayoutParams();
        lp1.weight = secondaryLayerWeight;
        mSecondaryLayer.setLayoutParams(lp2);
        mSecondaryLayer.requestLayout();
    }


    public TwoLayersAdapter<P, S> getTwoLayersAdapter() {
        return mTwoLayersAdapter;
    }

    public void setTwoLayersAdapter(TwoLayersAdapter<P, S> twoLayersAdapter) {
        mTwoLayersAdapter = twoLayersAdapter;

        mPrimaryData = mTwoLayersAdapter.setPrimaryData();
        mSecondaryData = mTwoLayersAdapter.setSecondaryData(mPrimaryData.get(0));

        mPrimaryAdapter = new RecyclerAdapter<P>(mPrimaryData) {
            @Override
            public AbstractVH<P> createViewHolder(ViewGroup parent) {
                return mTwoLayersAdapter.createPrimaryVH(parent);
            }
        };

        mSecondaryAdapter = new RecyclerAdapter<S>(mSecondaryData) {
            @Override
            public AbstractVH<S> createViewHolder(ViewGroup parent) {
                return mTwoLayersAdapter.createSecondaryVH(parent);
            }
        };

        mPrimaryLayer.setAdapter(mPrimaryAdapter);
        mSecondaryLayer.setAdapter(mSecondaryAdapter);
    }


    public void setPrimaryItemClickListener(RecyclerAdapter.OnItemClickListener<P> onItemClickListener) {
        mPrimaryAdapter.setOnItemClickListener(onItemClickListener);
    }

    public void setSecondaryItemClickListener(RecyclerAdapter.OnItemClickListener<S> onItemClickListener) {
        mSecondaryAdapter.setOnItemClickListener(onItemClickListener);
    }

    public P getCurPrimaryItem() {
        return mPrimaryAdapter.getCurItem();
    }

    public S getCurSecondaryItem() {
        return mSecondaryAdapter.getCurItem();
    }

    @Override
    public PopupWindow initPopup(Context context) {
        View v = View.inflate(context, R.layout.view_two_layers, null);
        mPrimaryLayer = (RecyclerView) v.findViewById(R.id.primary_layer);
        mSecondaryLayer = (RecyclerView) v.findViewById(R.id.secondary_layer);
        mPrimaryLayer.setLayoutManager(new LinearLayoutManager(context));
        mSecondaryLayer.setLayoutManager(new LinearLayoutManager(context));

        PopupWindow popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.layers_height), true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);

        return popupWindow;
    }
}
