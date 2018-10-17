package com.yxzc.tzl.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yxzc.tzl.utils.ObjectUtils;

import java.util.List;

/**
 * @Project: tzl_android
 * @Package: com.yxzc.tzl.base
 * @Author: HSL
 * @Time: 2018/09/28 14:59
 * @E-mail: 13967189624@163.com
 * @Description: RecyclerView基类
 */
public class BaseRecycleAdapter<T, BT extends ViewDataBinding> extends RecyclerView.Adapter<BindingViewHolder<BT>> {

    private Context context;
    private List<T> dataList;
    /**
     * 列表项布局id
     */
    private int layoutItemId;
    /**
     * item 对象id
     */
    private int variableId;

    public BaseRecycleAdapter(Context context, List<T> dataList, int layoutItemId, int variableId) {
        this.context = context;
        this.dataList = dataList;
        this.layoutItemId = layoutItemId;
        this.variableId = variableId;
    }

    public BaseRecycleAdapter(Context context, List<T> dataList) {
        this(context, dataList, 0, 0);
    }

    protected BindingViewHolder<BT> buildViewHolder(ViewGroup parent, int viewType, int layoutItemId, int variableId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        BT binding = DataBindingUtil.inflate(inflater, layoutItemId, parent, false);
        BindingViewHolder<BT> viewHolder = new BindingViewHolder<BT>(binding);
        viewHolder.setViewType(viewType);
        return viewHolder;
    }

    protected void bindViewHolder(BT binding, int variableId, int position) {
        binding.setVariable(variableId, dataList.get(position));
        binding.executePendingBindings();
    }

    @Override
    public BindingViewHolder<BT> onCreateViewHolder(ViewGroup parent, int viewType) {
        return buildViewHolder(parent, viewType, layoutItemId, variableId);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<BT> holder, int position) {
        bindViewHolder(holder.getBinding(), variableId, position);
    }

    @Override
    public int getItemCount() {
        return ObjectUtils.isEmpty(dataList) ? 0 : dataList.size();
    }

    public List<T> getDataList() {
        return this.dataList;
    }
}
