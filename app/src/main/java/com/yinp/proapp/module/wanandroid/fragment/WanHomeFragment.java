package com.yinp.proapp.module.wanandroid.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yinp.proapp.adapter.ComViewHolder;
import com.yinp.proapp.adapter.CommonAdapter;
import com.yinp.proapp.base.fragment.PresenterBaseFragment;
import com.yinp.proapp.bean.BannerEntity;
import com.yinp.proapp.databinding.FragmentWanHomeBinding;
import com.yinp.proapp.databinding.ItemWanHomeListBinding;
import com.yinp.proapp.module.wanandroid.WanManager;
import com.yinp.proapp.module.wanandroid.adapter.WanHomeBannerAdapter;
import com.yinp.proapp.module.wanandroid.bean.WanHomeListBean;
import com.yinp.proapp.utils.JumpWebUtils;
import com.yinp.proapp.web.retrofit.BaseObserver;
import com.yinp.proapp.web.retrofit.BaseRetrofitData;
import com.yinp.tools.utils.ToastUtil;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 玩Android的首页
 */
public class WanHomeFragment extends PresenterBaseFragment<FragmentWanHomeBinding, WanManager> {
    private WanHomeBannerAdapter bannerAdapter;
    private List<BannerEntity> listBanner = new ArrayList<>();

    private List<WanHomeListBean> dataList = new ArrayList<>();
    private CommonAdapter<WanHomeListBean> commonAdapter;

    private int size = 10;
    private int page = 1;

    public static WanHomeFragment getInstance() {
        WanHomeFragment wanHomeFragment = new WanHomeFragment();
        Bundle bundle = new Bundle();
        wanHomeFragment.setArguments(bundle);
        return wanHomeFragment;
    }

    @Override
    protected void initViews(View view) {
        initBanner();
        initRecycler();
        getBannerList();
        getListInfo();
        refresh();
    }

    private void initBanner() {
        bannerAdapter = new WanHomeBannerAdapter(listBanner, getContext());
        bd.topBanner.setAdapter(bannerAdapter).addBannerLifecycleObserver(this).setIndicator(new CircleIndicator(getContext()));
        bd.topBanner.setOnBannerListener((OnBannerListener<BannerEntity>) (data, position) -> {
            JumpWebUtils.startWebView(getContext(), data.getTitle(), data.getUrl());
        });
    }

    private void initRecycler() {
        commonAdapter = new CommonAdapter<WanHomeListBean>(getContext(), dataList) {
            @Override
            protected ComViewHolder setComViewHolder(View view, int viewType, ViewGroup parent) {
                return new ViewHolder(ItemWanHomeListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }

            @Override
            public void onBindItem(RecyclerView.ViewHolder holder, int position, WanHomeListBean item) {
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.binding.tvTitle.setText(item.getTitle());
                viewHolder.binding.tvAuthor.setText(TextUtils.isEmpty(item.getAuthor()) ? item.getShareUser() : item.getAuthor());
                viewHolder.binding.tvType.setText(item.getSuperChapterName() + "/" + item.getChapterName());
            }
        };
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(RecyclerView.VERTICAL);
        bd.baseRecycle.setLayoutManager(llm);
        bd.baseRecycle.setAdapter(commonAdapter);
    }

    class ViewHolder extends ComViewHolder {
        ItemWanHomeListBinding binding;

        public ViewHolder(ItemWanHomeListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    private void refresh() {
        //下拉刷新
        bd.baseRefresh.setRefreshHeader(new ClassicsHeader(getContext()));
        bd.baseRefresh.setRefreshFooter(new ClassicsFooter(getContext()));
        //为下来刷新添加事件
        bd.baseRefresh.setOnRefreshListener(refreshlayout -> {
            page = 1;
            getListInfo();
        });
        //为上拉加载添加事件
        bd.baseRefresh.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getListInfo();
        });
    }

    private void getBannerList() {
        showLoading("加载中");
        presenter.getBannerList(new BaseObserver<BaseRetrofitData>() {
            @Override
            public void onSuccess(BaseRetrofitData o) {
                hideLoading();
                if (o == null) {
                    return;
                }
                JsonElement jsonElement = o.getData();
                if (jsonElement.isJsonNull()) {
                    return;
                }
                Type type = new TypeToken<ArrayList<BannerEntity>>() {
                }.getType();
                listBanner = new Gson().fromJson(jsonElement, type);
                bannerAdapter.setDatas(listBanner);
                bannerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg) {
                hideLoading();
                ToastUtil.initToast(getContext(), msg);
            }

            @Override
            public void onCodeFail(String msg) {
                hideLoading();
                ToastUtil.initToast(getContext(), msg);
            }
        });
    }

    /**
     * 获取首页得数据
     */
    private void getListInfo() {
        presenter.getHomeList(size * page, new BaseObserver<BaseRetrofitData>() {
            @Override
            public void onSuccess(BaseRetrofitData o) {
                hideLoading();
                if (o == null) {
                    return;
                }
                JsonElement datas = o.getData().isJsonNull() ? null : o.getData().getAsJsonObject().get("datas").getAsJsonArray();
                if (datas.isJsonNull()) {
                    return;
                }
                Type type = new TypeToken<ArrayList<WanHomeListBean>>() {
                }.getType();
                ArrayList<WanHomeListBean> arrayList = new Gson().fromJson(datas, type);
                if (arrayList == null || arrayList.size() == 0) {
                    return;
                }
                if (page == 1) {
                    dataList.clear();
                    dataList.addAll(arrayList);
                } else {
                    dataList.addAll(arrayList);
                }
                bd.bottom.noLl.setVisibility(View.GONE);
                bd.baseRefresh.setVisibility(View.VISIBLE);
                commonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg) {

            }

            @Override
            public void onCodeFail(String msg) {

            }
        });
    }

    @Override
    protected WanManager createPresenter() {
        return new WanManager(getContext());
    }
}
