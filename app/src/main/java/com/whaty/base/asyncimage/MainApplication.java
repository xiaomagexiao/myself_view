package com.whaty.base.asyncimage;


import android.app.Application;
import android.graphics.Bitmap;

import com.whaty.base.asyncimage.MCCacheManager.CacheType;

public class MainApplication extends Application {
    private static Bitmap.CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT;
    private static int DISK_IMAGECACHE_QUALITY;
    private static int DISK_IMAGECACHE_SIZE;

    static {
        MainApplication.DISK_IMAGECACHE_SIZE = 10485760;
        MainApplication.DISK_IMAGECACHE_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
        MainApplication.DISK_IMAGECACHE_QUALITY = 100;
    }

    public MainApplication() {
        super();
    }

    private void createImageCache() {
        MCCacheManager.getInstance().init(this, this.getPackageCodePath(), MainApplication.DISK_IMAGECACHE_SIZE, MainApplication.DISK_IMAGECACHE_COMPRESS_FORMAT, MainApplication.DISK_IMAGECACHE_QUALITY, CacheType.MEMORY);
    }

    private void init() {
        this.createImageCache();
    }

    public void onCreate() {
        super.onCreate();
        this.init();
    }
}

