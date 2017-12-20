package com.cheny.demo.gank.android;

import com.cheny.demo.gank.base.BasePresenter;
import com.cheny.demo.gank.base.BaseView;
import com.cheny.demo.gank.model.AndroidAPI;

import java.util.List;

/**
 * Created by cheny on 2017/11/8.
 */

public class AndroidContract {

    public interface View extends BaseView<Presenter> {

        void showLoading();

        void showRefreshLoading();

        void hideLoading();

        void hideRefreshLoading();

        void showAndroidList(List<AndroidAPI> androidAPIS);

        void refreshAndroidList(List<AndroidAPI> androidAPIS);

        void showError(String msg);
    }

    public interface Presenter extends BasePresenter {

        void loadAndroidDatas(int pageSize, int pageIndex);

        void refreshAndroidDatas(int pageSize, int pageIndex);
    }
}
