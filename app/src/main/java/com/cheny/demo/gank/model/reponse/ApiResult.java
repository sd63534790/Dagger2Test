package com.cheny.demo.gank.model.reponse;

import java.io.Serializable;

/**
 * 接口返回实体基类
 * Created by cheny on 2017/10/19.
 */

public class ApiResult<T> implements Serializable {


    private boolean error;
    private T results;

    public ApiResult(boolean error, T results) {
        this.error = error;
        this.results = results;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public static <T> ApiResult successResult(T responseResult) {
        return new ApiResult(false, responseResult);
    }

    public static <T> ApiResult errorResult(T responseResult) {
        return new ApiResult(true, responseResult);
    }


}
