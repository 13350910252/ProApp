package com.yinp.proapp.module.wanandroid.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yinp.proapp.adapter.ComViewHolder;
import com.yinp.proapp.adapter.CommonAdapter;
import com.yinp.proapp.base.activity.PresenterBaseFragmentActivity;
import com.yinp.proapp.databinding.ActivityWanCollectionBinding;
import com.yinp.proapp.databinding.ItemWanCollectListBinding;
import com.yinp.proapp.module.wanandroid.WanManager;
import com.yinp.proapp.module.wanandroid.bean.CollectionListBean;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanData2;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanObserver2;
import com.yinp.proapp.utils.AppUtils;
import com.yinp.proapp.utils.HtmlImageGetter;
import com.yinp.proapp.utils.JumpWebUtils;
import com.yinp.proapp.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏
 */
public class WanCollectionActivity extends PresenterBaseFragmentActivity<ActivityWanCollectionBinding, WanManager> {
    private List<CollectionListBean.DatasBean> dataList = new ArrayList<>();
    private CommonAdapter<CollectionListBean.DatasBean> commonAdapter;

    private int page = 0;

    @Override
    protected WanManager createPresenter() {
        return new WanManager(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(mContext));
        initClick(this, bd.header.headerBackImg);
        initRecycler();
        refresh();
        getCollectInfo(true);
    }

    private void initRecycler() {
        commonAdapter = new CommonAdapter<CollectionListBean.DatasBean>(mContext, dataList) {
            @Override
            protected ComViewHolder setComViewHolder(View view, int viewType, ViewGroup parent) {
                return new ViewHolder(ItemWanCollectListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }

            @Override
            public void onBindItem(RecyclerView.ViewHolder holder, int position, CollectionListBean.DatasBean item) {
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.binding.tvTitle.setText(AppUtils.getValue(item.getTitle()));

                HtmlImageGetter htmlImageGetter = new HtmlImageGetter(viewHolder.binding.htvContent);
                htmlImageGetter.enableCompressImage(true, 100);
                viewHolder.binding.htvContent.setHtml(item.getDesc(), htmlImageGetter);

                viewHolder.binding.tvAuthor.setText(AppUtils.getValue(item.getAuthor()));
                viewHolder.binding.tvTime.setText(item.getNiceDate());
                viewHolder.binding.tvChapterName.setText(item.getChapterName());
                viewHolder.binding.ivCollect.setSelected(true);
                viewHolder.binding.htvContent.setOnClickListener(v -> {

                });
            }
        };
        commonAdapter.setOnItemClickListener(new ComViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                CollectionListBean.DatasBean item = dataList.get(position);
                JumpWebUtils.startWebView(mContext, item.getTitle(), item.getLink());
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(RecyclerView.VERTICAL);
        bd.baseRecycle.setLayoutManager(llm);
        bd.baseRecycle.setAdapter(commonAdapter);
    }

    static class ViewHolder extends ComViewHolder {
        ItemWanCollectListBinding binding;

        public ViewHolder(ItemWanCollectListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == bd.header.headerBackImg) {
            finish();
        }
    }

    private void refresh() {
        //下拉刷新
        bd.baseRefresh.setRefreshHeader(new ClassicsHeader(mContext));
        bd.baseRefresh.setRefreshFooter(new ClassicsFooter(mContext));
        //为下来刷新添加事件
        bd.baseRefresh.setOnRefreshListener(refreshlayout -> {
            page = 0;
            getCollectInfo(false);
        });
        //为上拉加载添加事件
        bd.baseRefresh.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getCollectInfo(false);
        });
    }

    /**
     * 获取收藏列表
     */
    private void getCollectInfo(boolean isLoad) {
        if (isLoad) {
            showLoading("加载中...");
        }
        presenter.getCollectedList(page, new WanObserver2<WanData2<CollectionListBean>>() {
            @Override
            public void onSuccess(WanData2<CollectionListBean> o) {
                hideLoading();
                if (page > 1)
                    bd.baseRefresh.finishLoadMore();
                if (o.getData() != null) {
                    if (o.getData().getDatas() != null && o.getData().getDatas().size() > 0) {
                        int start = dataList.size();
                        dataList.addAll(o.getData().getDatas());
                        int end = dataList.size();
                        commonAdapter.notifyItemRangeChanged(start, end);
                        bd.baseRefresh.setVisibility(View.VISIBLE);
                        bd.bottom.noLl.setVisibility(View.GONE);
                    }
                } else {
                    bd.baseRefresh.setVisibility(View.GONE);
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