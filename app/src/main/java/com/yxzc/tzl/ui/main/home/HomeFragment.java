package com.yxzc.tzl.ui.main.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yxzc.tzl.R;
import com.yxzc.tzl.base.BaseMVPFragment;
import com.yxzc.tzl.ui.login.LoginActivity;
import com.yxzc.tzl.utils.ToastUtils;
import com.yxzc.tzl.widgets.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Author: HouShengLi
 * @Time: 2018/9/27 15:37
 * @E-mail: 13967189624@163.com
 * @Description: 首页
 */
public class HomeFragment extends BaseMVPFragment<HomePresenter> implements HomeContract.IHomeView {

    Unbinder unbinder;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initExtraData(Bundle bundle) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected HomePresenter onCreatePresenter() {
        return new HomePresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        // TODO: 2018/10/15 测试
//        mPresenter.requestToastMsg();
    }

    @Override
    protected void initListener() {

    }

    @OnClick(R.id.test_tv)
    public void onViewClicked() {
        LoginActivity.start(getActivity());
//        testDialog();
    }

    /**
     * 测试Dialog
     */
    private void testDialog() {
        // TODO: 2018/10/8 随时可以删除
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
//        LoadingDialog loadingDialog = new LoadingDialog(getActivity(),0.6f,Gravity.BOTTOM);
        loadingDialog.showDialog();
    }

    /**
     * 测试~~
     *
     * @param msg
     */
    @Override
    public void testToast(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
