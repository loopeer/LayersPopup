package com.loopeer.android.popuputils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleTwoLayersPopup<P, S> extends TwoLayersPopup<P, S> {

    public SimpleTwoLayersPopup(Context context) {
        super(context);
    }

    @Override
    public void setPrimaryItemClickListener(final RecyclerAdapter.OnItemClickListener<P> onItemClickListener) {
        RecyclerAdapter.OnItemClickListener<P> listener = new RecyclerAdapter.OnItemClickListener<P>() {
            @Override
            public void onItemClick(P data, int position) {
                mSecondaryAdapter.updateData(mTwoLayersAdapter.setSecondaryData(data));
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(data, position);
            }
        };
        super.setPrimaryItemClickListener(listener);
    }

    @Override
    public void setTwoLayersAdapter(TwoLayersAdapter<P, S> twoLayersAdapter) {
        super.setTwoLayersAdapter(twoLayersAdapter);
        try{
            setPrimaryItemClickListener(null);
        } catch (NullPointerException e){
            //
        }
    }

    public abstract static class SimpleTwoLayersAdapter<P, S> implements TwoLayersAdapter<P, S> {
        @Override
        public AbstractVH<P> createPrimaryVH(ViewGroup parent) {
            return new PrimaryVH<>(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_primary_layer, parent, false));
        }

        @Override
        public AbstractVH<S> createSecondaryVH(ViewGroup parent) {
            return new SecondaryVH<>(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_secondary_layer, parent, false));
        }
    }

    private static class PrimaryVH<P> extends AbstractVH<P> {

        private TextView mFirstText;

        PrimaryVH(View itemView) {
            super(itemView);
            mFirstText = (TextView) itemView.findViewById(R.id.text_first_layer);
        }

        @Override
        public void bind(P item) {
            if (item != null) mFirstText.setText(item.toString());
        }
    }

    private static class SecondaryVH<S> extends AbstractVH<S> {

        private TextView mFirstText;

        SecondaryVH(View itemView) {
            super(itemView);
            mFirstText = (TextView) itemView.findViewById(R.id.text_second_layer);
        }

        @Override
        public void bind(S item) {
            if (item != null) mFirstText.setText(item.toString());
        }
    }

}
