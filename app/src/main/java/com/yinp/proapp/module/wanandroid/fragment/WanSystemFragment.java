package com.yinp.proapp.module.wanandroid.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.yinp.proapp.adapter.ComViewHolder;
import com.yinp.proapp.adapter.CommonAdapter;
import com.yinp.proapp.base.fragment.PresenterBaseFragment;
import com.yinp.proapp.databinding.FragmentWanSystemBinding;
import com.yinp.proapp.databinding.ItemSystemOneBinding;
import com.yinp.proapp.databinding.ItemSystemTwoBinding;
import com.yinp.proapp.module.wanandroid.WanManager;
import com.yinp.proapp.module.wanandroid.activity.WanSysActivity;
import com.yinp.proapp.module.wanandroid.bean.WanSysListBean;
import com.yinp.proapp.module.wanandroid.bean.WanSysListBean2;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanData2;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanObserver2;

import java.util.ArrayList;
import java.util.List;

public class WanSystemFragment extends PresenterBaseFragment<FragmentWanSystemBinding, WanManager> {
    private CommonAdapter<WanSysListBean2> adapter;
    private List<WanSysListBean2> dataList = new ArrayList<>();

    public static WanSystemFragment getInstance() {
        WanSystemFragment wanSystemFragment = new WanSystemFragment();
        Bundle bundle = new Bundle();
        wanSystemFragment.setArguments(bundle);
        return wanSystemFragment;
    }

    @Override
    protected WanManager createPresenter() {
        return new WanManager(getContext());
    }

    @Override
    protected void initViews(View view) {
        initRecycler();
        getSystemInfo();
    }

    private void initRecycler() {
        bd.bottom.noLl.setVisibility(View.GONE);
        adapter = new CommonAdapter<WanSysListBean2>(getContext(), dataList) {
            @Override
            protected ComViewHolder setComViewHolder(View view, int viewType, ViewGroup parent) {
                if (viewType == 0) {
                    return new ViewHolder(ItemSystemOneBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), 0);
                } else {
                    return new ViewHolder(ItemSystemTwoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), 1);
                }
            }

            @Override
            public int getItemViewType(int position) {
                if (dataList.get(position).isTitle()) {
                    return 0;
                } else {
                    return 1;
                }
            }

            @Override
            public void onBindItem(RecyclerView.ViewHolder holder, int position, WanSysListBean2 item) {
                super.onBindItem(holder, position, item);
                ViewHolder viewHolder = (ViewHolder) holder;
                if (viewHolder.oneBinding != null) {
                    viewHolder.oneBinding.tvTitle.setText(item.getName());
                } else {
                    viewHolder.twoBinding.stvValue.setText(item.getName());
                }
            }
        };
        adapter.setOnItemClickListener(new ComViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                WanSysListBean2 wanSysListBean2 = dataList.get(position);
                if (!wanSysListBean2.isTitle()) {
                    Bundle bundle = new Bundle();
                    goToActivity(WanSysActivity.class, bundle);
                }
            }
        });
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//主轴为水平方向，起点在左端。
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//按正常方向换行
        //justifyContent 属性定义了项目在主轴上的对齐方式。
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。
        bd.rvList.setLayoutManager(flexboxLayoutManager);
        bd.rvList.setAdapter(adapter);
    }

    private static class ViewHolder extends ComViewHolder {
        ItemSystemOneBinding oneBinding = null;
        ItemSystemTwoBinding twoBinding = null;

        public ViewHolder(ViewBinding itemView, int position) {
            super(itemView.getRoot());
            if (position == 0) {
                twoBinding = null;
                oneBinding = (ItemSystemOneBinding) itemView;
            } else {
                oneBinding = null;
                twoBinding = (ItemSystemTwoBinding) itemView;
            }
        }
    }

    private void getSystemInfo() {
        showLoading("加载中...");
        presenter.getSystemInfo(new WanObserver2<WanData2<List<WanSysListBean>>>() {
            @Override
            public void onSuccess(WanData2<List<WanSysListBean>> o) {
                hideLoading();
                if (o.getData() != null || o.getData().size() != 00) {
                    List<WanSysListBean> listBeans = o.getData();
                    dataList.clear();
                    for (int i = 0; i < listBeans.size(); i++) {
                        WanSysListBean wanSysListBean = listBeans.get(i);
                        dataList.add(new WanSysListBean2(wanSysListBean.getCourseId(), wanSysListBean.getId(), wanSysListBean.getName(), wanSysListBean.getOrder()
                                , wanSysListBean.getParentChapterId(), wanSysListBean.isUserControlSetTop(), wanSysListBean.getVisible(), true));
                        if (wanSysListBean.getChildren() != null) {
                            for (int i1 = 0; i1 < wanSysListBean.getChildren().size(); i1++) {
                                WanSysListBean.ChildrenBean childrenBean = wanSysListBean.getChildren().get(i1);
                                dataList.add(new WanSysListBean2(childrenBean.getCourseId(), childrenBean.getId(), childrenBean.getName(), childrenBean.getOrder()
                                        , childrenBean.getParentChapterId(), childrenBean.isUserControlSetTop(), childrenBean.getVisible(), false));
                            }
                        }
                    }
                    bd.rvList.setVisibility(View.VISIBLE);
                    bd.bottom.noLl.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                } else {
                    bd.rvList.setVisibility(View.GONE);
                    bd.bottom.noLl.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String msg) {
                hideLoading();
                showToast(msg);
            }

            @Override
            public void onCodeFail(String msg) {
                hideLoading();
                showToast(msg);
            }
        });
    }
}
