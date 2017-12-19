package com.cheny.demo.gank.android;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cheny.demo.gank.R;
import com.cheny.demo.gank.model.AndroidAPI;
import com.cheny.demo.gank.view.adapter.AndroidListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by cheny on 2017/11/8.
 */

public class AndroidFragment extends Fragment implements AndroidContract.View {
    View view;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout mSwiprefresh;
    Unbinder unbinder;
    private AndroidListAdapter mMeizhiListAdapter;
    AndroidPresenter mAndroidPresenter;
    List<AndroidAPI> mAndroidAPIS;
    private boolean mIsFirstTimeTouchBottom = true;
    private int pageIndex = 1;
    private boolean isLoading = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_android, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initRecyclerView();
        initSwipeRefresh();
        loadAndroidDatas();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initViews() {
        mAndroidPresenter = new AndroidPresenter(this);
        mAndroidAPIS = new ArrayList<>();
    }

    private void initRecyclerView() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(staggeredGridLayoutManager);
        mMeizhiListAdapter = new AndroidListAdapter(getActivity(), mAndroidAPIS);
        mRecyclerview.setAdapter(mMeizhiListAdapter);
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                boolean isBottom =
//                    staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1] >=
//                        mMeizhiListAdapter.getItemCount() - 6;
//                Log.e("lastComplete", String.valueOf(staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1]));
//                Log.e("getItemCount", String.valueOf(mMeizhiListAdapter.getItemCount()));
//                if (isBottom) {
//                    if (!mIsFirstTimeTouchBottom) {
//                        Log.e("onScrolled", "列表底部");
//                    } else {
//                        mIsFirstTimeTouchBottom = false;
//                        Log.e("onScrolled", "第一次");
//                    }
//
//
//                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (!isLoading && !recyclerView.canScrollVertically(1)) {
                    mAndroidPresenter.loadAndroidDatas(10, ++pageIndex);
                }
            }
        });
    }

    private void initSwipeRefresh() {
        if (mSwiprefresh != null) {
            mSwiprefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
            mSwiprefresh.setOnRefreshListener(() -> mAndroidPresenter.refreshAndroidDatas(10, 1));
        }
    }

    private void loadAndroidDatas() {
        mAndroidPresenter.loadAndroidDatas(10, pageIndex);
    }


    @Override
    public void setPresenter(AndroidContract.Presenter presenter) {

    }

    @Override
    public void showAndroidList(List<AndroidAPI> androidAPIS) {
        if (androidAPIS.size() > 0) {
            mAndroidAPIS.addAll(androidAPIS);
            mMeizhiListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void refreshAndroidList(List<AndroidAPI> androidAPIS) {
        if (androidAPIS.size() > 0) {
            mAndroidAPIS.clear();
            mAndroidAPIS.addAll(androidAPIS);
            mMeizhiListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        isLoading = true;
        mSwiprefresh.setRefreshing(true);

    }

    @Override
    public void showRefreshLoading() {
        isLoading = true;
        mSwiprefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        isLoading = false;
        mSwiprefresh.setRefreshing(false);
    }

    @Override
    public void hideRefreshLoading() {
        isLoading = false;
        mSwiprefresh.setRefreshing(false);
    }
}
