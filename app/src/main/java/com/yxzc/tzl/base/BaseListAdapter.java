package com.yxzc.tzl.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yxzc.tzl.utils.ObjectUtils;

import java.util.List;

/**
 * @Project: tzl_android
 * @Package: com.yxzc.tzl.base
 * @Author: HSL
 * @Time: 2018/09/28 15:14
 * @E-mail: 13967189624@163.com
 * @Description:
 */
public class BaseListAdapter<T, BT extends ViewDataBinding> extends BaseAdapter {

    private Context context;
    private List<T> datalist;
    /**
     * 列表项布局id
     */
    private int layoutItemId;
    /**
     * item 对象id
     */
    private int variableId;

    public BaseListAdapter(Context context, List<T> datalist, int layoutItemId, int variableId) {
        this.context = context;
        this.datalist = datalist;
        this.layoutItemId = layoutItemId;
        this.variableId = variableId;
    }

    protected void getItemView(int position, BT binding) {

    }

    @Override
    public int getCount() {
        return ObjectUtils.isEmpty(datalist) ? 0 : datalist.size();
    }

    @Override
    public T getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            BT binding;
            if (convertView == null) {
                binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutItemId, parent, false);
            } else {
                binding = DataBindingUtil.getBinding(convertView);
            }
            binding.setVariable(variableId, datalist.get(position));
            getItemView(position, binding);
            return binding.getRoot();
        } catch (Exception e) {
            // TODO: 2018/9/28
        }
        return convertView;
    }

    public List<T> getDatalist() {
        return this.datalist;
    }
}
