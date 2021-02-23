package com.example.mvvmdemo.util;

import com.example.mvvmdemo.App;

public class CacheUtil {
    /**
     * 获取系统默认缓存文件夹内的缓存大小
     */
    public static String getTotalCacheSize() {
        long cacheSize = FileUtils.getSize(App.getInstance().getCacheDir());
        if (FileUtils.isSDCardAlive()) {
            cacheSize += FileUtils.getSize(App.getInstance().getExternalCacheDir());
        }
        return FileUtils.formatSize(cacheSize);
    }

    public static void clearAllCache() {
        FileUtils.delete(App.getInstance().getCacheDir());
        if (FileUtils.isSDCardAlive()) {
            FileUtils.delete(App.getInstance().getExternalCacheDir());
        }
    }
}