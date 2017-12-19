package com.cheny.demo.gank.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheny.demo.gank.R;
import com.cheny.demo.gank.model.AndroidAPI;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cheny on 2017/11/8.
 */

public class AndroidListAdapter extends RecyclerView.Adapter<AndroidListAdapter.ViewHolder> {


    private List<AndroidAPI> mAndroidAPIS;
    private Context mContext;

    public AndroidListAdapter(Context context, List<AndroidAPI> androidAPIS) {
        mContext = context;
        mAndroidAPIS = androidAPIS;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_android, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AndroidAPI androidAPI = mAndroidAPIS.get(position);
        Glide.with(mContext).load(androidAPI.getImageUrl()).into(holder.mIvCover);
        holder.mTvContent.setText(androidAPI.getDesc());

    }

    @Override
    public int getItemCount() {
        return mAndroidAPIS.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivCover)
        ImageView mIvCover;
        @BindView(R.id.tvContent)
        TextView mTvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
