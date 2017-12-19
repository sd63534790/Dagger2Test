package com.cheny.demo.gank.model.reponse;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by cheny on 2017/11/8.
 */

public abstract class HttpObserver<T> implements Observer<T> {


    @Override
    public void onNext(@NonNull T t) {
        if (t instanceof ApiResult) {
            //onSuccess
            if (((ApiResult) t).isError()) {
                onError(new ExceptionHandle.ResponeThrowable(new Throwable("服务端返回错误"), ExceptionHandle.ERROR.SERVER_ERROR));
            } else {
                //onError
                onNext(t);
            }
        }
        Log.e("tag", "onNext");
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Log.e("tag", "MySubscriber.throwable =" + e.toString());
        Log.e("tag", "MySubscriber.throwable =" + e.getMessage());

        if (e instanceof Exception) {
            //访问获得对应的Exception
            onError(ExceptionHandle.handleException(e));
        } else {
            //将Throwable 和 未知错误的status code返回
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        Log.e("tag", "onSubscribe");

    }


    public abstract void onError(ExceptionHandle.ResponeThrowable responeThrowable);

}
