package com.yxzc.tzl.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @Time: 2018/09/28 15:02
 * @E-mail: 13967189624@163.com
 * @Description:
 */
public class BindingViewHolder<BT extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private BT mBinding;
    private int viewType = 0;

    public BindingViewHolder(BT binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public BindingViewHolder(View view) {
        super(view);
    }

    public BT getBinding() {
        return mBinding;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
