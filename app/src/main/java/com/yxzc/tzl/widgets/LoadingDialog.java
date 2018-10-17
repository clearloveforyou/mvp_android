package com.yxzc.tzl.widgets;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yxzc.tzl.R;
import com.yxzc.tzl.base.BaseDialog;

/**
 * @Project: tzl_android
 * @Package: com.yxzc.tzl.widgets
 * @Author: HSL
 * @Time: 2018/10/08 10:42
 * @E-mail: 13967189624@163.com
 * @Description: 网络加载等待时的dialog
 */
public class LoadingDialog extends BaseDialog {

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public LoadingDialog(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
    }

    @Override
    protected void initView(Context context) {
        View content = mLayoutInflater.inflate(R.layout.dialog_loading_net, null);
//        int width = (int) mContext.getResources().getDimension(R.dimen.spacing_260);
//        int height = (int) mContext.getResources().getDimension(R.dimen.spacing_160);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
//        setContentView(content, layoutParams);
        setCanceledOnTouchOutside(false);
        setContentView(content);
    }
}
