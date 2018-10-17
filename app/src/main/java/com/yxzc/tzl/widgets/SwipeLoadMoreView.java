package com.yxzc.tzl.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

/**
 * @Project: tzl_android
 * @Package: com.yxzc.tzl.widgets
 * @Author: HSL
 * @Time: 2018/10/15 12:03
 * @E-mail: 13967189624@163.com
 * @Description:自定义SwipeMenuRecyclerView加载更多时显示的View
 */
public class SwipeLoadMoreView extends LinearLayout implements SwipeMenuRecyclerView.LoadMoreView {

    public SwipeLoadMoreView(Context context) {
        super(context);
    }

    public SwipeLoadMoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeLoadMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 马上开始回调加载更多了
     * 这里应该显示进度条。
     */
    @Override
    public void onLoading() {

    }

    /**
     * 加载更多完成了。
     *
     * @param dataEmpty 是否请求到空数据。
     * @param hasMore   是否还有更多数据等待请求。
     */
    @Override
    public void onLoadFinish(boolean dataEmpty, boolean hasMore) {
        // 根据参数，显示没有数据的提示、没有更多数据的提示。
        // 如果都不存在，则都不用显示。
    }

    /**
     * 调用了setAutoLoadMore(false)后，在需要加载更多的时候，此方法被调用，并传入listener。
     */
    @Override
    public void onWaitToLoadMore(SwipeMenuRecyclerView.LoadMoreListener loadMoreListener) {

    }

    /**
     * 加载出错啦，下面的错误码和错误信息二选一。
     *
     * @param errorCode    错误码。
     * @param errorMessage 错误信息。
     */
    @Override
    public void onLoadError(int errorCode, String errorMessage) {

    }
}
