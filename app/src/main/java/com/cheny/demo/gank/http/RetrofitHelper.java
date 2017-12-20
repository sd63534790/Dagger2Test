package com.cheny.demo.gank.http;

import com.cheny.demo.gank.model.AndroidAPI;
import com.cheny.demo.gank.model.reponse.ApiResult;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by cy on 2017/12/20.
 */

public class RetrofitHelper {


    private ApiInterface apiInterface;

    public RetrofitHelper(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public Observable<ApiResult<List<AndroidAPI>>> getAndroidData(int pageSize, int pageIndex) {
        return apiInterface.androidApi(pageSize, pageIndex);
    }
}
