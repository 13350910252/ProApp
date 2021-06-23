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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(mContext));
    }

    @Override
    protected void initViews() {
        initClick(this, bd.header.headerBackImg, bd.tvTwoOne);
        bd.header.headerCenterTitle.setText("自定义view");
        initRecycler();
    }

    private void initRecycler() {
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
        adapterOther.setOnItemClickListener(new ComViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                CustomViewBean bean = listOther.get(position);
                Intent intent = new Intent();
                intent.setClassName(mContext, bean.getUrl());
                startActivity(intent);
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        bd.rvOther.setLayoutManager(llm);
        bd.rvOther.setAdapter(adapterOther);
    }

    class OtherViewHolder extends ComViewHolder {
        ItemCustomViewListBinding binding;

        public OtherViewHolder(ItemCustomViewListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == bd.header.headerBackImg) {
            finish();
        } else if (v == bd.tvTwoOne) {
            goToActivity(TestTriangleActivity.class);
        }
    }
}