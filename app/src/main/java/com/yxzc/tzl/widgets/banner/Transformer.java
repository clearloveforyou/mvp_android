package com.yxzc.tzl.widgets.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.yxzc.tzl.widgets.banner.transformer.AccordionTransformer;
import com.yxzc.tzl.widgets.banner.transformer.BackgroundToForegroundTransformer;
import com.yxzc.tzl.widgets.banner.transformer.CubeInTransformer;
import com.yxzc.tzl.widgets.banner.transformer.CubeOutTransformer;
import com.yxzc.tzl.widgets.banner.transformer.DefaultTransformer;
import com.yxzc.tzl.widgets.banner.transformer.DepthPageTransformer;
import com.yxzc.tzl.widgets.banner.transformer.FlipHorizontalTransformer;
import com.yxzc.tzl.widgets.banner.transformer.FlipVerticalTransformer;
import com.yxzc.tzl.widgets.banner.transformer.ForegroundToBackgroundTransformer;
import com.yxzc.tzl.widgets.banner.transformer.RotateDownTransformer;
import com.yxzc.tzl.widgets.banner.transformer.RotateUpTransformer;
import com.yxzc.tzl.widgets.banner.transformer.ScaleInOutTransformer;
import com.yxzc.tzl.widgets.banner.transformer.StackTransformer;
import com.yxzc.tzl.widgets.banner.transformer.TabletTransformer;
import com.yxzc.tzl.widgets.banner.transformer.ZoomInTransformer;
import com.yxzc.tzl.widgets.banner.transformer.ZoomOutSlideTransformer;
import com.yxzc.tzl.widgets.banner.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
