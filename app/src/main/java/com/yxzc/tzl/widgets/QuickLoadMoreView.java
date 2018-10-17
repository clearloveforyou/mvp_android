package com.yxzc.tzl.widgets;

import com.chad.library.adapter.base.loadmore.LoadMoreView;

/**
 * @Project: tzl_android
 * @Package: com.yxzc.tzl.widgets
 * @Author: HSL
 * @Time: 2018/10/15 11:58
 * @E-mail: 13967189624@163.com
 * @Description:自定义BaseQuickAdapter加载更多显示的View
 */
public class QuickLoadMoreView extends LoadMoreView {

    /**
     * 自定义布局
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return 0;
    }

    /**
     * 加载中
     *
     * @return
     */
    @Override
    protected int getLoadingViewId() {
        return 0;
    }

    /**
     * 加载失败
     *
     * @return
     */
    @Override
    protected int getLoadFailViewId() {
        return 0;
    }

    /**
     * 全部加载完毕
     *
     * @return
     */
    @Override
    protected int getLoadEndViewId() {
        return 0;
    }
}
