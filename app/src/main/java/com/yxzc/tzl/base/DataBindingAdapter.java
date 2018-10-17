package com.yxzc.tzl.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxzc.tzl.R;

import java.util.List;

/**
 * @Project: tzl_android
 * @Package: com.yxzc.tzl.base
 * @Author: HSL
 * @Time: 2018/10/15 11:26
 * @E-mail: 13967189624@163.com
 * @Description:
 */
public class DataBindingAdapter<T> extends BaseQuickAdapter<T, DataBindingAdapter.DataBindingVH> {

    private int mVariableId;

    public DataBindingAdapter(int layoutResId, int variableId, @Nullable List<T> data) {
        super(layoutResId, data);
        mVariableId = variableId;
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }

    @Override
    protected void convert(DataBindingVH helper, T item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(mVariableId, item);
        binding.executePendingBindings();
        onExtraBind(helper, item);
    }

    /**
     * 额外还需要操作控件，重写此方法
     *
     * @param helper
     * @param item
     */
    protected void onExtraBind(DataBindingVH helper, T item) {

    }

    public static class DataBindingVH extends BaseViewHolder {

        public DataBindingVH(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}
