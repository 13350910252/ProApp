package com.yinp.proapp.module.wanandroid.fragment;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.yinp.proapp.databinding.FragmentWanNavigationBinding;
import com.yinp.proapp.databinding.ItemSystemOneBinding;
import com.yinp.proapp.databinding.ItemSystemTwoBinding;
import com.yinp.proapp.module.wanandroid.WanManager;
import com.yinp.proapp.module.wanandroid.bean.NavigationListBean;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanData2;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanObserver2;
import com.yinp.proapp.utils.JumpWebUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩安卓导航也买你
 */
public class WanNavigationFragment extends PresenterBaseFragment<FragmentWanNavigationBinding, WanManager> {
    private CommonAdapter<NavigationBean> adapter;
    private List<NavigationBean> dataList = new ArrayList<>();

    @Override
    protected WanManager createPresenter() {
        return new WanManager(getContext());
    }

    public static WanNavigationFragment getInstance() {
        WanNavigationFragment wanNavigationFragment = new WanNavigationFragment();
        Bundle bundle = new Bundle();
        wanNavigationFragment.setArguments(bundle);
        return wanNavigationFragment;
    }

    @Override
    protected void initViews(View view) {
        initRecycler();
        getNavigationList();
    }

    private void initRecycler() {
        bd.bottom.noLl.setVisibility(View.GONE);
        adapter = new CommonAdapter<NavigationBean>(getContext(), dataList) {
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
                if (TextUtils.isEmpty(dataList.get(position).title)) {
                    return 0;
                } else {
                    return 1;
                }
            }

            @Override
            public void onBindItem(RecyclerView.ViewHolder holder, int position, NavigationBean item) {
                super.onBindItem(holder, position, item);
                ViewHolder viewHolder = (ViewHolder) holder;
                if (viewHolder.oneBinding != null) {
                    viewHolder.oneBinding.tvTitle.setText(item.name);
                } else {
                    viewHolder.twoBinding.stvValue.setText(item.title);
                    viewHolder.twoBinding.stvValue.setOnClickListener(v -> {
                        JumpWebUtils.startWebView(getContext(), item.title, item.link);
                    });
                }
            }
        };
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
        ItemSystemOneBinding oneBinding;
        ItemSystemTwoBinding twoBinding;

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

    /**
     * 获取导航列表
     */
    private void getNavigationList() {
        showLoading("加载中...");
        presenter.getNavigationList(new WanObserver2<WanData2<List<NavigationListBean>>>() {
            @Override
            public void onSuccess(WanData2<List<NavigationListBean>> o) {
                List<NavigationListBean> list = o.getData();
                if (list != null && list.size() > 0) {
                    dataList.clear();
                    for (int i = 0; i < list.size(); i++) {
                        NavigationListBean navigationListBean = list.get(i);
                        dataList.add(new NavigationBean(navigationListBean.getName()));
                        if (navigationListBean.getArticles() != null && navigationListBean.getArticles().size() > 0) {
                            for (int i1 = 0; i1 < navigationListBean.getArticles().size(); i1++) {
                                NavigationListBean.ArticlesBean childrenBean = navigationListBean.getArticles().get(i1);
                                dataList.add(new NavigationBean(childrenBean.getTitle(), childrenBean.getLink()));
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                hideLoading();
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

    static class NavigationBean {
        public String name;
        public String title;
        public String link;

        public NavigationBean(String name) {
            this.name = name;
        }

        public NavigationBean(String title, String link) {
            this.title = title;
            this.link = link;
        }
    }
}
