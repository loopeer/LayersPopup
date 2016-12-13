package com.loopeer.android.popuputils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.PopupWindow;

public abstract class BasePopup {
    private PopupWindow mPopupWindow;

    public BasePopup(Context context) {
        mPopupWindow = initPopup(context);
        if (mPopupWindow == null) {
            throw new NullPointerException("must init popup window.");
        }
    }

    public void setPopSize(int width, int height) {
        mPopupWindow.setWidth(width);
        mPopupWindow.setHeight(height);
    }

    public void setFocusable(boolean focusable) {
        mPopupWindow.setFocusable(focusable);
    }

    public void setBackgroundDrawable(Drawable drawable) {
        mPopupWindow.setBackgroundDrawable(drawable);
    }

    public void setOutsideTouchable(boolean touchable) {
        mPopupWindow.setOutsideTouchable(touchable);
    }

    private void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        mPopupWindow.setOnDismissListener(onDismissListener);
    }

    public void setInputMethodMode(int mode) {
        mPopupWindow.setInputMethodMode(mode);
    }

    public boolean isShowing() {
        return mPopupWindow.isShowing();
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        mPopupWindow.showAtLocation(parent, gravity, x, y);
    }

    public void update() {
        mPopupWindow.update();
    }

    public void show(View anchor, int xOff, int yOff) {
        mPopupWindow.showAsDropDown(anchor, xOff, yOff);
    }

    public void dismiss() {
        mPopupWindow.dismiss();
    }

    public abstract PopupWindow initPopup(Context context);
}
