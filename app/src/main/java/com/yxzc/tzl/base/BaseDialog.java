package com.yxzc.tzl.base;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.yxzc.tzl.R;


/**
 * @author vondear
 */
public class BaseDialog extends Dialog {

    protected Context mContext;
    protected LayoutParams mLayoutParams;
    protected LayoutInflater mLayoutInflater;

    /**
     * 默认样式
     *
     * @param context
     */
    public BaseDialog(Context context) {
        this(context, R.style.base_dialog);
    }

    /**
     * 自定义样式
     *
     * @param context
     * @param themeResId
     */
    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        // TODO: 2018/10/8 默认0.6,居中
        initBaseDialog(context, 0.6f, Gravity.CENTER);
    }

    /**
     * 默认样式
     * 指定内容区域外的透明度
     * Dialog位置
     *
     * @param context
     * @param alpha   设置内容区域以外的背景 0.0f--1f(不透明)
     * @param gravity 方向(Gravity.BOTTOM,Gravity.TOP,Gravity.LEFT,Gravity.RIGHT)
     */
    public BaseDialog(Context context, float alpha, int gravity) {
        super(context, R.style.base_dialog);
        initBaseDialog(context, alpha, gravity);
    }

    private void initBaseDialog(Context context, float alpha, int gravity) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        Window window = this.getWindow();
        mLayoutParams = window.getAttributes();
        window.setDimAmount(alpha);
        window.setAttributes(mLayoutParams);
        if (mLayoutParams != null) {
            mLayoutParams.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
            mLayoutParams.gravity = gravity;
        }
        //用于初始化控件
        initView(context);
    }

    /**
     * 用于加载布局，初始化控件
     *
     * @param context
     */
    protected void initView(Context context) {
        //重写
    }

    /**
     * 设置全屏显示
     */
    public void setFullScreen() {
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        LayoutParams lp = window.getAttributes();
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }

    /**
     * 设置宽度match_parent
     */
    public void setFullScreenWidth() {
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        LayoutParams lp = window.getAttributes();
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    /**
     * 设置高度为match_parent
     */
    public void setFullScreenHeight() {
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        LayoutParams lp = window.getAttributes();
        lp.width = LayoutParams.WRAP_CONTENT;
        lp.height = LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }

    /**
     * 获取LayoutParams便于根据需求设置
     * 相对于对于原始位置的偏移.
     *
     * @return
     */
    public LayoutParams getLayoutParams() {
        return mLayoutParams;
    }

    public void setOnWhole() {
        getWindow().setType(LayoutParams.TYPE_SYSTEM_ALERT);
    }

    /**
     * show dialog
     */
    public void showDialog() {
        show();
    }
}
