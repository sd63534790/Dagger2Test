package com.cheny.demo.gank.http;

import com.cheny.demo.gank.model.AndroidAPI;
import com.cheny.demo.gank.model.WelfareAPI;
import com.cheny.demo.gank.model.reponse.ApiResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    String HOST = "http://gank.io/api/data/";

    @GET("Android/{pageSize}/{pageIndex}")
    Observable<ApiResult<List<AndroidAPI>>> androidApi(@Path("pageSize") int pageSize, @Path("pageIndex") int pageIndex);

    @GET("福利/{pageSize}/{pageIndex}")
    Observable<ApiResult<List<WelfareAPI>>> welfareApi(@Path("pageSize") int pageSize, @Path("pageIndex") int pageIndex);

}
