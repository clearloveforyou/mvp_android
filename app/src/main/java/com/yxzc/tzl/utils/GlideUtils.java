package com.yxzc.tzl.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yxzc.tzl.R;

/**
 * @Project: tzl_android
 * @Package: com.yxzc.tzl.utils
 * @Author: HSL
 * @Time: 2018/09/28 14:45
 * @E-mail: 13967189624@163.com
 * @Description:
 */
public final class GlideUtils {

    public static void setCircleImage(Context context, String url, ImageView view) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .circleCrop().dontAnimate();
        Glide.with(context).load(url).apply(requestOptions).into(view);
    }

    public static void setImage(Context context, String url, ImageView view) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .dontAnimate();
        Glide.with(context).load(url).apply(requestOptions).into(view);
    }

    /**
     * 绑定头像
     *
     * @param context
     * @param uri
     * @param riv
     */
    public static void bindHeadPortrait(Context context, Uri uri, RoundedImageView riv) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .dontAnimate();
        Glide.with(context).load(uri).apply(requestOptions).into(riv);
    }
}
