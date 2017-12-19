package com.cheny.demo.gank.http;

import com.cheny.demo.gank.BuildConfig;
import com.cheny.demo.gank.base.BaseApplication;
import com.cheny.demo.gank.common.C;
import com.cheny.demo.gank.common.utils.Util;
import com.cheny.demo.gank.model.AndroidAPI;
import com.cheny.demo.gank.model.WelfareAPI;
import com.cheny.demo.gank.model.reponse.ApiResult;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {

    private static ApiInterface sApiService = null;
    private static Retrofit sRetrofit = null;
    private static OkHttpClient sOkHttpClient = null;

    private void init() {
        initOkHttp();
        initRetrofit();
        sApiService = sRetrofit.create(ApiInterface.class);
    }

    private RetrofitSingleton() {
        init();
    }

    public static RetrofitSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RetrofitSingleton INSTANCE = new RetrofitSingleton();
    }

    private static void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 缓存 http://www.jianshu.com/p/93153b34310e
        File cacheFile = new File(C.NET_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = chain -> {
            Request request = chain.request();
            if (!Util.isNetworkConnected(BaseApplication.getAppContext())) {
                request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            }
            Response response = chain.proceed(request);
            Response.Builder newBuilder = response.newBuilder();
            if (Util.isNetworkConnected(BaseApplication.getAppContext())) {
                int maxAge = 0;
                // 有网络时 设置缓存超时时间0个小时
                newBuilder.header("Cache-Control", "public, max-age=" + maxAge);
            } else {
                // 无网络时，设置超时为4周
                int maxStale = 60 * 60 * 24 * 28;
                newBuilder.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
            }
            return newBuilder.build();
        };
        builder.cache(cache).addInterceptor(cacheInterceptor);
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        sOkHttpClient = builder.build();
    }

    private static void initRetrofit() {
        sRetrofit = new Retrofit.Builder()
            .baseUrl(ApiInterface.HOST)
            .client(sOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
    }

    /**
     * 获取Android数据
     *
     * @param pageSize
     * @param pageIndex
     * @return
     */
    public Observable<ApiResult<List<AndroidAPI>>> finAndroidList(int pageSize, int pageIndex) {
        return sApiService.androidApi(pageSize, pageIndex);

    }

    /**
     * 获取福利数据
     *
     * @param pageSize
     * @param pageIndex
     * @return
     */
    public Observable<ApiResult<List<WelfareAPI>>> finWelfareApiList(int pageSize, int pageIndex) {
        return sApiService.welfareApi(pageSize, pageIndex);

    }
}
