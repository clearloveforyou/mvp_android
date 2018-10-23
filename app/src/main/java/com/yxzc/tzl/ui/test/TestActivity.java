package com.yxzc.tzl.ui.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding3.widget.RxTextView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yxzc.tzl.R;
import com.yxzc.tzl.base.BaseMVPActivity;
import com.yxzc.tzl.beans.CalendarItem;
import com.yxzc.tzl.beans.FlagEvent;
import com.yxzc.tzl.beans.TvCategoryItem;
import com.yxzc.tzl.constants.MainCode;
import com.yxzc.tzl.db.manager.TestManager;
import com.yxzc.tzl.ui.main.Main;
import com.yxzc.tzl.utils.ActivityUtils;
import com.yxzc.tzl.utils.GlideUtils;
import com.yxzc.tzl.utils.ObjectUtils;
import com.yxzc.tzl.widgets.ImageSelectDialog;
import com.yxzc.tzl.widgets.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class TestActivity extends BaseMVPActivity<TestPresenter> implements TestContract.ITestView {

    @BindView(R.id.test_event_tv)
    TextView testEventTv;
    @BindView(R.id.test_http_tv)
    TextView testHttpTv;
    @BindView(R.id.test_dialog_tv)
    TextView testDialogTv;
    @BindView(R.id.test_portrait_tv)
    TextView testPortraitTv;
    @BindView(R.id.head_portrait_riv)
    RoundedImageView headPortraitRiv;
    @BindView(R.id.refresh_test)
    SmartRefreshLayout refreshTest;
    @BindView(R.id.test_db_tv)
    TextView testDbTv;
    @BindView(R.id.test_search_et)
    EditText testSearchEt;
    @BindView(R.id.test_search_tv)
    TextView testSearchTv;

    private ImageSelectDialog mImageSelectDialog;

    public static void start(Context context) {
        Intent starter = new Intent(context, TestActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected TestPresenter onCreatePresenter() {
        return new TestPresenter();
    }

    @Override
    protected void initExtraData() {

    }

    @Override
    protected void initView() {
        //测试搜索
        testSearch();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mImageSelectDialog.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 测试
     * 1.结束到某个activity
     * 2.eventBus
     * 3.主页面的切换
     */
    @OnClick(R.id.test_event_tv)
    public void onTestEventClicked() {
        Activity main = ActivityUtils.getTargetActivity(Main.class);
        if (!ObjectUtils.isEmpty(main)) {
            ActivityUtils.finishToActivity(main, false);
        }
        FlagEvent<Integer> event = new FlagEvent<>();
        event.setKey(MainCode.SWITCH_MAIN_TAB);
        event.setPageClass(getClass());
        event.setData(2);
        EventBus.getDefault().post(event);
    }

    /**
     * 网络测试
     */
    @OnClick(R.id.test_http_tv)
    public void onTestHttpClicked() {
//        //请求日历
//        mPresenter.requestCalendar("2018-3-10");
        //电视台分类
        mPresenter.requestTvCategory();
    }

    /**
     * 测试dialog
     */
    @OnClick(R.id.test_dialog_tv)
    public void onTestDialogClicked() {
        testDialog();
    }

    /**
     * 头像处理
     */
    @OnClick(R.id.test_portrait_tv)
    public void onTestPortraitClicked() {
        if (mImageSelectDialog == null) {
            mImageSelectDialog = new ImageSelectDialog(mActivity) {
                @Override
                protected void onSelectImageResult(boolean isSuccess, Uri uri) {
                    GlideUtils.bindHeadPortrait(mActivity, uri, headPortraitRiv);
                }
            };
        }
        mImageSelectDialog.showDialog();
    }

    /**
     * 日历请求成功
     *
     * @param calendarItem
     */
    @Override
    public void requestCalendarSuccess(CalendarItem calendarItem) {
        Logger.t("CA").e("错误：==%s", calendarItem);
    }

    /**
     * 电视台分类
     *
     * @param items
     */
    @Override
    public void requestTVSuccess(List<TvCategoryItem> items) {
        Logger.t("CA").e("错误：==%s", items);
    }

    /**
     * 测试Dialog
     */
    private void testDialog() {
        // TODO: 2018/10/8 随时可以删除
        LoadingDialog loadingDialog = new LoadingDialog(mActivity);
//        LoadingDialog loadingDialog = new LoadingDialog(getActivity(),0.6f,Gravity.BOTTOM);
        loadingDialog.showDialog();
    }

    /**
     * 测试数据库
     */
    @OnClick(R.id.test_db_tv)
    public void onTestDBClicked() {
        TestManager.saveTest("测试");
    }

    /**
     * 测试搜索
     */
    private void testSearch() {
        Disposable subscribe = RxTextView.textChanges(testSearchEt)//当EditText发生改变
                //每500毫秒发射一次
                //仅在过了一段指定的时间还没发射数据时才发射一个数据
                //如果原始Observable在这个新生成的Observable终止之前发射了另一个数据， debounce 会抑制(suppress)这个数据项。
                .debounce(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())//内容监听操作需要在主线程操作
                //过滤掉EditText没有内容Observable
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        return !ObjectUtils.isEmpty(charSequence);
                    }
                })
                //switchMap操作符：只发射最近的一个请求
                .switchMap(new Function<CharSequence, ObservableSource<CharSequence>>() {

                    @Override
                    public ObservableSource<CharSequence> apply(CharSequence charSequence) throws Exception {
                        return Observable.just(charSequence);
                    }
                })
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        testSearchTv.setText(charSequence);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        String message = throwable.getMessage();
                    }
                });
    }
}
