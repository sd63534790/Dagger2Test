package com.cheny.demo.gank.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cheny.demo.gank.R;

import butterknife.ButterKnife;

/**
 * Created by cheny on 2017/11/8.
 */

public class FirstFragment extends Fragment {

    View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_first, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }
}
