package com.yxzc.tzl.utils;

import com.orhanobut.logger.Logger;
import com.yxzc.tzl.application.AppContext;

import java.io.File;
import java.util.Locale;

/**
 * @Author: HouShengLi
 * @Time: 2018/10/9 14:30
 * @E-mail: 13967189624@163.com
 * @Description:应用缓存处理
 */
public class AppCacheUtils {

    /**
     * 清除缓存
     */
    public void clearAllCache() {
        try {
            //清除内部缓存
            //directory: /data/data/package/cache
            CleanUtils.cleanInternalCache();
            //清除外部缓存
            //directory: /storage/emulated/0/android/data/package/cache
            CleanUtils.cleanExternalCache();
            //清除自定义目录
            FileUtils.deleteAllInDir(CatalogUtils.getRootDir());
        } catch (Exception e) {
            Logger.e("clear all cache error:%s", e);
        }
    }

    /**
     * 获取缓存大小
     *
     * @return
     */
    public String getCacheSize() {
        //内部缓存
        //directory: /data/data/package/cache
        long dirInSideSize = FileUtils.getDirLength(AppContext.getInstance().getCacheDir());
        //外部缓存
        //directory: /storage/emulated/0/android/data/package/cache
        long dirOutSideSize = 0;
        if (SDCardUtils.hasSDCard()) {
            dirOutSideSize = FileUtils.getDirLength(AppContext.getInstance().getExternalCacheDir());
        }
        //自定义目录
        long dirCustomSize = 0;
        File customDir = CatalogUtils.getRootDir();
        if (!ObjectUtils.isEmpty(customDir)) {
            dirCustomSize = FileUtils.getDirLength(customDir);
        }
        long allCacheSize = dirInSideSize + dirOutSideSize + dirCustomSize;
        return byte2FitMemorySize(allCacheSize);
    }

    private String byte2FitMemorySize(final long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < 1024) {
            return String.format(Locale.getDefault(), "%.3fB", (double) byteNum);
        } else if (byteNum < 1048576) {
            return String.format(Locale.getDefault(), "%.3fKB", (double) byteNum / 1024);
        } else if (byteNum < 1073741824) {
            return String.format(Locale.getDefault(), "%.3fMB", (double) byteNum / 1048576);
        } else {
            return String.format(Locale.getDefault(), "%.3fGB", (double) byteNum / 1073741824);
        }
    }
}
