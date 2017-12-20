package com.cheny.demo.gank.module;

import com.cheny.demo.gank.BuildConfig;
import com.cheny.demo.gank.android.AndroidContract;
import com.cheny.demo.gank.base.BaseApplication;
import com.cheny.demo.gank.common.C;
import com.cheny.demo.gank.common.utils.Util;
import com.cheny.demo.gank.http.ApiInterface;
import com.cheny.demo.gank.http.RetrofitHelper;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cy on 2017/12/19.
 */
@Module
public class HttpModule {

    private AndroidContract.View mView;

    public HttpModule(AndroidContract.View view) {
        this.mView = view;
    }

    @Singleton
    @Provides
    OkHttpClient.Builder providesOkHttpClinetBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    File providesCacheFie() {
        return new File(C.NET_CACHE);
    }

    @Singleton
    @Provides
    Cache providesCache(File cacheFile) {
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        return cache;
    }

    @Singleton
    @Provides
    OkHttpClient providesOkHttpClinet(OkHttpClient.Builder builder, Cache cache) {
        // 缓存 http://www.jianshu.com/p/93153b34310e
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
        return builder.build();
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }


    @Singleton
    @Provides
    Retrofit provideGankRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, ApiInterface.HOST);
    }


    @Singleton
    @Provides
    ApiInterface providesApiInterface(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }

    @Singleton
    @Provides
    RetrofitHelper providesRetrofitHelper(ApiInterface apiInterface) {
        return new RetrofitHelper(apiInterface);
    }

    @Singleton
    @Provides
    AndroidContract.View providesView() {
        return this.mView;
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
