package com.loopeer.android.popuputils;

import android.view.ViewGroup;

import java.util.List;

public interface TwoLayersAdapter<P, S> {
    List<P> setPrimaryData();

    List<S> setSecondaryData(P p);

    AbstractVH<P> createPrimaryVH(ViewGroup parent);

    AbstractVH<S> createSecondaryVH(ViewGroup parent);
}
