package com.cheny.demo.gank.android;

import android.support.annotation.NonNull;

import com.cheny.demo.gank.http.RetrofitSingleton;
import com.cheny.demo.gank.model.AndroidAPI;
import com.cheny.demo.gank.model.WelfareAPI;
import com.cheny.demo.gank.model.reponse.ApiResult;
import com.cheny.demo.gank.model.reponse.ExceptionHandle;
import com.cheny.demo.gank.model.reponse.HttpObserver;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by cheny on 2017/11/8.
 */

public class AndroidPresenter implements AndroidContract.Presenter {

    @NonNull
    private final AndroidContract.View mView;

    public AndroidPresenter(@NonNull AndroidContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadAndroidDatas(int pageSize, int pageIndex) {
        mView.showLoading();
        Observable.zip(RetrofitSingleton.getInstance()
            .finAndroidList(pageSize, pageIndex), RetrofitSingleton.getInstance()
            .finWelfareApiList(pageSize, pageIndex), (listApiResult, listApiResult2) -> mergeData(listApiResult, listApiResult2))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new HttpObserver<ApiResult<List<AndroidAPI>>>() {
                @Override
                public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                    mView.showError(responeThrowable.getMessage());
                    mView.hideLoading();
                }

                @Override
                public void onNext(ApiResult<List<AndroidAPI>> listApiResult) {
                    mView.showAndroidList(listApiResult.getResults());
                    mView.hideLoading();
                }
            });
    }

    @Override
    public void refreshAndroidDatas(int pageSize, int pageIndex) {
        mView.showRefreshLoading();
        Observable.zip(RetrofitSingleton.getInstance()
            .finAndroidList(pageSize, pageIndex), RetrofitSingleton.getInstance()
            .finWelfareApiList(pageSize, pageIndex), (listApiResult, listApiResult2) -> mergeData(listApiResult, listApiResult2))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new HttpObserver<ApiResult<List<AndroidAPI>>>() {
                @Override
                public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                    mView.showError(responeThrowable.getMessage());
                    mView.hideRefreshLoading();
                }

                @Override
                public void onNext(ApiResult<List<AndroidAPI>> listApiResult) {
                    mView.refreshAndroidList(listApiResult.getResults());
                    mView.hideRefreshLoading();
                }
            });
    }

    /**
     * 合并数据
     */
    private ApiResult<List<AndroidAPI>> mergeData(ApiResult<List<AndroidAPI>> androidResult, ApiResult<List<WelfareAPI>> welfareResult) {
        for (int i = 0; i < androidResult.getResults().size(); i++) {
            androidResult.getResults().get(i).setImageUrl(welfareResult.getResults().get(i).getUrl());
        }
        return androidResult;
    }
}
