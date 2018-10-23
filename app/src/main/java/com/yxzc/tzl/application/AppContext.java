package com.yxzc.tzl.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;
import com.yxzc.tzl.R;
import com.yxzc.tzl.base.CatalogParam;
import com.yxzc.tzl.db.DbManager;
import com.yxzc.tzl.services.SafeHostnameVerifier;
import com.yxzc.tzl.services.SafeTrustManager;
import com.yxzc.tzl.utils.AppUtils;
import com.yxzc.tzl.utils.CatalogUtils;
import com.yxzc.tzl.utils.CrashUtils;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * @Author: HouShengLi
 * @Time: 2018/9/27 11:23
 * @E-mail: 13967189624@163.com
 * @Description:初始化
 */
public class AppContext extends BaseAppContext {

    private static AppContext instance;

    /**
     * 获取全局上下文
     *
     * @return
     */
    public static AppContext getInstance() {
        return instance;
    }

    static {
        ////目录配置
        ////目录配置
        CatalogUtils.setOnCatalogInitListener(new CatalogUtils.OnCatalogInitListener() {
            @Override
            public CatalogParam getStorageInit() {
                CatalogParam catalogParam = new CatalogParam();
                catalogParam.setAppDir("tangzhanglao");
                return catalogParam;
            }
        });
        ////刷新控件配置
        ////刷新控件配置
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                //全局设置（优先级最低）
                layout.setEnableLoadMore(false);
                layout.setEnableAutoLoadMore(false);
                layout.setEnableOverScrollDrag(false);
                layout.setEnableOverScrollBounce(true);
            }
        });
        //设置全局头部
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
                ClassicsHeader classicsHeader = new ClassicsHeader(context);
                return classicsHeader;
            }
        });

        ////todo 其他...
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化工作
        //初始化工作
        //初始化工作
        initLeakCanary();
        initAppCrash();
        initLogger();
        initGreenDao();
        initOKGO();
    }

    /**
     * 内存泄漏检测
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    /**
     * App捕捉异常处理
     */
    @SuppressLint("MissingPermission")
    private void initAppCrash() {
        CrashUtils.init(CatalogUtils.getErrorDir(), new CrashUtils.OnCrashListener() {
            @Override
            public void onCrash(String crashInfo, Throwable e) {
                //打印日志、重启应用
                Logger.e("崩溃日志：%s", crashInfo);
                AppUtils.relaunchApp();
            }
        });
    }

    /**
     * 初始化Logger
     */
    private void initLogger() {
        PrettyFormatStrategy.Builder newBuilder = PrettyFormatStrategy.newBuilder();
        //（可选）是否显示线程信息。 默认值为true
        newBuilder.showThreadInfo(true);
        //（可选）要显示的方法行数。 默认2
        newBuilder.methodCount(2);
        //（可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
        newBuilder.methodOffset(0);
        //（可选）每个日志的全局标记。 默认PRETTY_LOGGER（如上图）
        newBuilder.tag("LOG_TZL");
        FormatStrategy formatStrategy = newBuilder.build();
        //修改全局的TAG
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        //控制打印开关
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });
        //保存log到文件 /storage/emulated/0
        Logger.addLogAdapter(new DiskLogAdapter());
    }

    /**
     * 初始化数据库
     */
    private void initGreenDao() {
        DbManager.getInstance(this);
    }

    /**
     * 初始化OkGo
     */
    private void initOKGO() {
        ////公共头部
        // TODO: 2018/10/12 header不支持中文，不允许有特殊字符
        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");

        ////公共参数
        // TODO: 2018/10/12 param支持中文,直接传,不要自己编码
        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");
//        params.put("commonParamsKey2", "这里支持中文参数");

        ////OkHttp相关配置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        //添加OkGo默认debug日志
        builder.addInterceptor(loggingInterceptor);

        ////超时时间设置，默认60秒
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        ////自动管理cookie（或者叫session的保持）
        //使用sp保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        //使用数据库保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
        //使用内存保持cookie，app退出后，cookie消失
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));

        ////https相关设置，以下几种方案根据需要自己设置
        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        //方法二：自定义信任规则，校验服务端证书
        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);

        ////配置https的域名匹配规则
        builder.hostnameVerifier(new SafeHostnameVerifier());

        // 其他统一的配置
        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                      //全局公共头
                .addCommonParams(params);

    }

}
