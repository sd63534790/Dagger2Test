package com.cheny.demo.gank.base;

import android.app.Application;
import android.content.Context;

import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;

import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by cheny on 2017/11/8.
 */

public class BaseApplication extends Application {

    private static String sCacheDir;
    private static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        LeakCanary.install(this);
        RxJavaPlugins.setErrorHandler(throwable -> {
            if (throwable != null) {
            } else {
            }
        });
        /*
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */
        if (getApplicationContext().getExternalCacheDir() != null && ExistSDCard()) {
            sCacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            sCacheDir = getApplicationContext().getCacheDir().toString();
        }

    }

    private boolean ExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static Context getAppContext() {
        return sAppContext;
    }

    public static void setAppContext(Context appContext) {
        sAppContext = appContext;
    }

    public static String getAppCacheDir() {
        return sCacheDir;
    }
}
