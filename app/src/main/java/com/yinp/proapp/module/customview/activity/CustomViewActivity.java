package com.yinp.proapp.module.customview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yinp.proapp.adapter.ComViewHolder;
import com.yinp.proapp.adapter.CommonAdapter;
import com.yinp.proapp.base.activity.AppBaseActivity;
import com.yinp.proapp.databinding.ActivityCustomViewBinding;
import com.yinp.proapp.databinding.ItemCustomViewListBinding;
import com.yinp.proapp.module.customview.bean.CustomViewBean;
import com.yinp.proapp.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的view
 */
public class CustomViewActivity extends AppBaseActivity<ActivityCustomViewBinding> {

    private List<CustomViewBean> listOther = new ArrayList<>();
    private CommonAdapter<CustomViewBean> adapterOther;

    private List<CustomViewBean> listMe = new ArrayList<>();
    private CommonAdapter<CustomViewBean> adapterMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(mContext));
    }

    @Override
    protected void initViews() {
        initClick(this, bd.header.headerBackImg);
        bd.header.headerCenterTitle.setText("自定义view");
        initRecycler();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == bd.header.headerBackImg) {
            finish();
        }
    }

    private void initRecycler() {
        /**
         * 复制加更改的
         */
        listOther.add(new CustomViewBean("点击屏幕显示点击范围的图片，可以滑动，显示范围随着滑动改变", "com.yinp.proapp.module.customview.activity.TestClipCircleActivity"));
        adapterOther = new CommonAdapter<CustomViewBean>(this, listOther) {
            @Override
            protected ComViewHolder setComViewHolder(View view, int viewType, ViewGroup parent) {
                return new OtherViewHolder(ItemCustomViewListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }

            @Override
            public void onBindItem(RecyclerView.ViewHolder holder, int position, CustomViewBean item) {
                OtherViewHolder viewHolder = (OtherViewHolder) holder;
                viewHolder.binding.tvTitle.setText(item.getTitle());
            }
        };
        adapterOther.setOnItemClickListener((position, view) -> {
            CustomViewBean bean = listOther.get(position);
            Intent intent = new Intent();
            intent.setClassName(mContext, bean.getUrl());
            startActivity(intent);
        });
        LinearLayoutManager llm = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        bd.rvOther.setLayoutManager(llm);
        bd.rvOther.setAdapter(adapterOther);
        /**
         * 自己做的自定义view
         */
        listMe.add(new CustomViewBean("带三角形的圆角布局","com.yinp.proapp.module.customview.activity.TestTriangleActivity"));
        adapterMe = new CommonAdapter<CustomViewBean>(this, listMe) {
            @Override
            protected ComViewHolder setComViewHolder(View view, int viewType, ViewGroup parent) {
                return new MeViewHolder(ItemCustomViewListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }

            @Override
            public void onBindItem(RecyclerView.ViewHolder holder, int position, CustomViewBean item) {
                OtherViewHolder viewHolder = (OtherViewHolder) holder;
                viewHolder.binding.tvTitle.setText(item.getTitle());
            }
        };
        adapterMe.setOnItemClickListener((position, view) -> {
            CustomViewBean bean = listMe.get(position);
            Intent intent = new Intent();
            intent.setClassName(mContext, bean.getUrl());
            startActivity(intent);
        });
        LinearLayoutManager llm2 = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        bd.rvMe.setLayoutManager(llm2);
        bd.rvMe.setAdapter(adapterMe);
    }

    static class OtherViewHolder extends ComViewHolder {
        ItemCustomViewListBinding binding;

        public OtherViewHolder(ItemCustomViewListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    static class MeViewHolder extends ComViewHolder {
        ItemCustomViewListBinding binding;

        public MeViewHolder(ItemCustomViewListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}