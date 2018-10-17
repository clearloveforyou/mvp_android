package com.yxzc.tzl.ui.main.manage;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yxzc.tzl.R;
import com.yxzc.tzl.base.BaseFragment;

/**
 * @Author: HouShengLi
 * @Time: 2018/9/27 15:39
 * @E-mail: 13967189624@163.com
 * @Description: 理财
 */
public class ManageFragment extends BaseFragment {


    public ManageFragment() {
        // Required empty public constructor
    }

    public static ManageFragment newInstance() {
        Bundle args = new Bundle();
        ManageFragment fragment = new ManageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage, container, false);
    }

}
