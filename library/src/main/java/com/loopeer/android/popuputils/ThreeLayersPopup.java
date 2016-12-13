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

public class ThreeLayersPopup<P, S, T> extends BasePopup {
    protected RecyclerView mPrimaryLayer;
    protected RecyclerView mSecondaryLayer;
    protected RecyclerView mTertiaryLayer;

    protected List<P> mPrimaryData;
    protected List<S> mSecondaryData;
    protected List<T> mTertiaryData;

    protected RecyclerAdapter<P> mPrimaryAdapter;
    protected RecyclerAdapter<S> mSecondaryAdapter;
    protected RecyclerAdapter<T> mTertiaryAdapter;
    protected ThreeLayersAdapter<P, S, T> mThreeLayersAdapter;

    public ThreeLayersPopup(Context context) {
        super(context);
    }

    public void setLayerWeight(int primaryLayerWeight, int secondaryLayerWeight, int tertiaryAdapter) {
        LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) mPrimaryLayer.getLayoutParams();
        lp1.weight = primaryLayerWeight;
        mPrimaryLayer.setLayoutParams(lp1);
        mPrimaryLayer.requestLayout();

        LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) mSecondaryLayer.getLayoutParams();
        lp1.weight = secondaryLayerWeight;
        mSecondaryLayer.setLayoutParams(lp2);
        mSecondaryLayer.requestLayout();


        LinearLayout.LayoutParams lp3 = (LinearLayout.LayoutParams) mTertiaryLayer.getLayoutParams();
        lp1.weight = tertiaryAdapter;
        mTertiaryLayer.setLayoutParams(lp3);
        mTertiaryLayer.requestLayout();
    }

    public ThreeLayersAdapter<P, S, T> getThreeLayersAdapter() {
        return mThreeLayersAdapter;
    }

    public void setThreeLayersAdapter(ThreeLayersAdapter<P, S, T> threeLayersAdapter) {
        mThreeLayersAdapter = threeLayersAdapter;

        mPrimaryData = mThreeLayersAdapter.setPrimaryData();
        mSecondaryData = mThreeLayersAdapter.setSecondaryData(mPrimaryData.get(0));
        mTertiaryData = mThreeLayersAdapter.setTertiaryData(mSecondaryData.get(0));

        mPrimaryAdapter = new RecyclerAdapter<P>(mPrimaryData) {
            @Override
            public AbstractVH<P> createViewHolder(ViewGroup parent) {
                return mThreeLayersAdapter.createPrimaryVH(parent);
            }
        };

        mSecondaryAdapter = new RecyclerAdapter<S>(mSecondaryData) {
            @Override
            public AbstractVH<S> createViewHolder(ViewGroup parent) {
                return mThreeLayersAdapter.createSecondaryVH(parent);
            }
        };

        mTertiaryAdapter = new RecyclerAdapter<T>(mTertiaryData) {
            @Override
            public AbstractVH<T> createViewHolder(ViewGroup parent) {
                return mThreeLayersAdapter.createTertiaryVH(parent);
            }
        };

        mPrimaryLayer.setAdapter(mPrimaryAdapter);
        mSecondaryLayer.setAdapter(mSecondaryAdapter);
        mTertiaryLayer.setAdapter(mTertiaryAdapter);
    }

    public void setPrimaryItemClickListener(RecyclerAdapter.OnItemClickListener<P> onItemClickListener) {
        mPrimaryAdapter.setOnItemClickListener(onItemClickListener);
    }

    public void setSecondaryItemClickListener(RecyclerAdapter.OnItemClickListener<S> onItemClickListener) {
        mSecondaryAdapter.setOnItemClickListener(onItemClickListener);
    }

    public void setTertiaryItemClickListener(RecyclerAdapter.OnItemClickListener<T> onItemClickListener) {
        mTertiaryAdapter.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public PopupWindow initPopup(Context context) {
        View v = View.inflate(context, R.layout.view_three_layers, null);
        mPrimaryLayer = (RecyclerView) v.findViewById(R.id.primary_layer);
        mSecondaryLayer = (RecyclerView) v.findViewById(R.id.secondary_layer);
        mTertiaryLayer = (RecyclerView) v.findViewById(R.id.tertiary_layer);

        mPrimaryLayer.setLayoutManager(new LinearLayoutManager(context));
        mSecondaryLayer.setLayoutManager(new LinearLayoutManager(context));
        mTertiaryLayer.setLayoutManager(new LinearLayoutManager(context));

        PopupWindow popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.layers_height), true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);

        return popupWindow;
    }
}
