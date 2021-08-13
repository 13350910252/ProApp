package com.yinp.proapp.module.wanandroid.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yinp.proapp.adapter.ComViewHolder;
import com.yinp.proapp.adapter.CommonAdapter;
import com.yinp.proapp.base.fragment.PresenterBaseFragment;
import com.yinp.proapp.databinding.FragmentWanSquareBinding;
import com.yinp.proapp.databinding.ItemWanSquareBinding;
import com.yinp.proapp.module.wanandroid.WanManager;
import com.yinp.proapp.module.wanandroid.bean.WanSquareListBean;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanData2;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanObserver2;

import java.util.ArrayList;
import java.util.List;

public class WanSquareFragment extends PresenterBaseFragment<FragmentWanSquareBinding, WanManager> {
    private List<WanSquareListBean.DatasBean> dataList = new ArrayList<>();
    private CommonAdapter<WanSquareListBean.DatasBean> adapter;
    private int page = 0;

    public static WanSquareFragment getInstance() {
        WanSquareFragment wanSquareFragment = new WanSquareFragment();
        Bundle bundle = new Bundle();
        wanSquareFragment.setArguments(bundle);
        return wanSquareFragment;
    }

    @Override
    protected WanManager createPresenter() {
        return new WanManager(getContext());
    }

    @Override
    protected void initViews(View view) {
        initRecycler();
        refresh();
    }

    private void initRecycler() {
        adapter = new CommonAdapter<WanSquareListBean.DatasBean>(getContext(), dataList) {
            @Override
            protected ComViewHolder setComViewHolder(View view, int viewType, ViewGroup parent) {
                ItemWanSquareBinding itemWanSquareBinding = ItemWanSquareBinding.inflate(LayoutInflater.from(getContext()), parent, false);
                return new ViewHolder(itemWanSquareBinding);
            }

            @Override
            public void onBindItem(RecyclerView.ViewHolder holder, int position, WanSquareListBean.DatasBean item) {
                ViewHolder viewHolder = (ViewHolder) holder;
                if (item.isFresh()) {
                    viewHolder.binding.tvLatest.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.binding.tvLatest.setVisibility(View.GONE);
                }
                if (item.isCollect()) {
                    viewHolder.binding.ivCollect.setSelected(true);
                } else {
                    viewHolder.binding.ivCollect.setSelected(false);
                }
                viewHolder.binding.tvTime.setText(item.getTitle());
                viewHolder.binding.tvSharePerson.setText("分享人：" + (TextUtils.isEmpty(item.getAuthor()) ? (TextUtils.isEmpty(item.getShareUser()) ? "暂无" : item.getShareUser()) : item.getAuthor()));
                viewHolder.binding.tvTime.setText(item.getNiceDate());
            }
        };
        adapter.setOnItemClickListener((position, view) -> {

        });
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(RecyclerView.VERTICAL);
        bd.baseRecycle.setLayoutManager(llm);
        bd.baseRecycle.setHasFixedSize(true);
        bd.baseRecycle.setAdapter(adapter);
    }

    class ViewHolder extends ComViewHolder {
        ItemWanSquareBinding binding;

        public ViewHolder(ViewBinding itemView) {
            super(itemView.getRoot());
            binding = (ItemWanSquareBinding) itemView;
        }
    }

    private void refresh() {
        //下拉刷新
        bd.baseRefresh.setRefreshHeader(new ClassicsHeader(getContext()));
        bd.baseRefresh.setRefreshFooter(new ClassicsFooter(getContext()));
        //为下来刷新添加事件
        bd.baseRefresh.setOnRefreshListener(refreshlayout -> {
            page = 0;
            getSquareList(false);
        });
        //为上拉加载添加事件
        bd.baseRefresh.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getSquareList(false);
        });
    }

    /**
     * 获取导航列表
     */
    private void getSquareList(boolean isLoad) {
        if (isLoad) {
            showLoading("加载中...");
        }
        presenter.getSquareList(page, new WanObserver2<WanData2<WanSquareListBean>>() {
            @Override
            public void onSuccess(WanData2<WanSquareListBean> o) {
                if (o.getData() != null) {
                    List<WanSquareListBean.DatasBean> list = o.getData().getDatas();
                    if (list != null && list.size() > 0) {
                        int length = dataList.size();
                        if (page == 0) {
                            dataList.clear();
                            dataList.addAll(list);
                            adapter.notifyDataSetChanged();
                        } else {
                            dataList.addAll(list);
                            adapter.notifyItemRangeChanged(length, dataList.size());
                        }
                    }
                }
                if (isLoad) {
                    hideLoading();
                }
            }

            @Override
            public void onError(String msg) {
                if (isLoad) {
                    hideLoading();
                }
                showToast(msg);
            }

            @Override
            public void onCodeFail(String msg) {
                if (isLoad) {
                    hideLoading();
                }
                showToast(msg);
            }
        });
    }
}
