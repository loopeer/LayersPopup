package com.loopeer.android.popuputils;

import android.view.ViewGroup;

import java.util.List;

public interface ThreeLayersAdapter<P, S, T> extends TwoLayersAdapter<P, S> {

    List<T> setTertiaryData(S s);

    AbstractVH<T> createTertiaryVH(ViewGroup parent);
}
