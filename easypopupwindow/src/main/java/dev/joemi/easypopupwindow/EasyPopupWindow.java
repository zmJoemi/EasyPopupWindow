package dev.joemi.easypopupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Joemi on 2017/4/9.
 */

public class EasyPopupWindow implements PopupWindow.OnDismissListener {
    public final static float DEFAULTBACKGROUNDALPHA = 0.7f;

    private Context mContext;
    private int windowWidth = 0;
    private int windowHeight = 0;
    private View mContentView;
    private boolean outsideTouchable = false;
    private boolean focusable=false;
    private float backgroundDarkValue = 0;//0-1, 背景变暗的值
    private int animationStyle = -1;
    private PopupWindow.OnDismissListener onDismissListener;

    private Window window;

    private PopupWindow popupWindow;

    private EasyPopupWindow(Context mContext) {
        this.mContext = mContext;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public boolean isShowing(){
        if (popupWindow!=null)
            return popupWindow.isShowing();
        return false;
    }

    private PopupWindow build() {

        Activity activity = (Activity) mContentView.getContext();
        if (activity != null && backgroundDarkValue > 0 && backgroundDarkValue < 1) {
            window = activity.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.alpha = backgroundDarkValue;
            window.setAttributes(params);
        }

        if (windowWidth != 0 && windowHeight != 0)
            popupWindow = new PopupWindow(mContentView, windowWidth, windowHeight);
        else
            popupWindow = new PopupWindow(mContentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (animationStyle!=-1)
            popupWindow.setAnimationStyle(animationStyle);
        popupWindow.setFocusable(focusable);
        // TODO: 2017/4/08
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(outsideTouchable);

        if (windowWidth == 0 || windowHeight == 0) {
            popupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            //如果外面没有设置宽高的情况下，计算宽高并赋值
            windowWidth = popupWindow.getContentView().getMeasuredWidth();
            windowHeight = popupWindow.getContentView().getMeasuredHeight();
        }

        // 添加dissmiss 监听
        popupWindow.setOnDismissListener(this);
        popupWindow.update();

        return popupWindow;
    }

    public EasyPopupWindow showAsDropDown(View anchor) {
        if (popupWindow != null) {
            popupWindow.showAsDropDown(anchor);
        }
        return this;
    }


    public EasyPopupWindow showAsDropDown(View anchor, int xoff, int yoff) {
        if (popupWindow != null) {
            popupWindow.showAsDropDown(anchor, xoff, yoff);
        }
        return this;
    }

    public EasyPopupWindow showAtLocation(View parent, int gravity, int x, int y) {
        if (popupWindow != null)
            popupWindow.showAtLocation(parent, gravity, x, y);
        return this;
    }


    @Override
    public void onDismiss() {
        if (onDismissListener != null)
            onDismissListener.onDismiss();

        if (window != null) {
            //dissmiss的还原背景alpha
            WindowManager.LayoutParams params = window.getAttributes();
            params.alpha = 1.0f;
            window.setAttributes(params);
        }

        if (popupWindow != null && popupWindow.isShowing())
            popupWindow.dismiss();
    }

    public static class Builder {
        private EasyPopupWindow easyPopupWindow;

        public Builder(Context mContext) {
            easyPopupWindow = new EasyPopupWindow(mContext);
        }

        public Builder size(int width, int height) {
            easyPopupWindow.windowWidth = width;
            easyPopupWindow.windowHeight = height;
            return this;
        }


        public Builder setView(int resLayoutId) {
            easyPopupWindow.mContentView = LayoutInflater.from(easyPopupWindow.mContext).inflate(resLayoutId, null);
            return this;
        }

        public Builder setView(View view) {
            easyPopupWindow.mContentView = view;
            return this;
        }

        public Builder setOutsideTouchable(boolean outsideTouchable) {
            easyPopupWindow.outsideTouchable = outsideTouchable;
            return this;
        }

        public Builder setFocusable(boolean focusable){
            easyPopupWindow.focusable=focusable;
            return this;
        }

        public Builder withBackgroundAlpha(float alpha) {
            easyPopupWindow.backgroundDarkValue = alpha;
            return this;
        }

        public Builder setAnimationStyle(int animationStyle){
            easyPopupWindow.animationStyle=animationStyle;
            return this;
        }

        public Builder setOnDimissListener(PopupWindow.OnDismissListener onDimissListener) {
            easyPopupWindow.onDismissListener = onDimissListener;
            return this;
        }

        public EasyPopupWindow build() {
            //构建PopWindow
            easyPopupWindow.build();
            return easyPopupWindow;
        }


    }
}
