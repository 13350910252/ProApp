package com.yinp.proapp.module.wanandroid.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.yinp.proapp.R;
import com.yinp.proapp.adapter.ComViewHolder;
import com.yinp.proapp.adapter.CommonAdapter;
import com.yinp.proapp.base.activity.PresenterBaseActivity;
import com.yinp.proapp.constant.HttpUrl;
import com.yinp.proapp.databinding.ActivityWanRankBinding;
import com.yinp.proapp.databinding.ItemRankListBinding;
import com.yinp.proapp.module.wanandroid.WanManager;
import com.yinp.proapp.module.wanandroid.bean.RankListBean;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanData2;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanObserver2;
import com.yinp.proapp.utils.JumpWebUtils;
import com.yinp.proapp.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩Android积分排行榜
 */
public class WanRankActivity extends PresenterBaseActivity<ActivityWanRankBinding, WanManager> {

    private List<RankListBean.DatasBean> dataList = new ArrayList<>();
    private CommonAdapter<RankListBean.DatasBean> commonAdapter;
    private int page = 1;
    private int maxCount;

    @Override
    protected WanManager createPresenter() {
        return new WanManager(mContext);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(mContext));
        bd.header.headerCenterTitle.setText("积分排行榜");
        bd.header.headerEnd.setImageResource(R.mipmap.ic_guize);
        initClick(this, bd.header.headerBackImg, bd.header.headerEnd);
        initRecycler();
        refresh();
        getRankList(true);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == bd.header.headerBackImg) {
            finish();
        } else if (v == bd.header.headerEnd) {
            JumpWebUtils.startWebView(mContext, "积分规则", HttpUrl.INTEGRAL_HELP_URL);
        }
    }

    private void initRecycler() {
        bd.bottom.noLl.setVisibility(View.GONE);
        commonAdapter = new CommonAdapter<RankListBean.DatasBean>(mContext, dataList) {
            @Override
            protected ComViewHolder setComViewHolder(View view, int viewType, ViewGroup parent) {
                return new ViewHolder(ItemRankListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }

            @Override
            public void onBindItem(RecyclerView.ViewHolder holder, int position, RankListBean.DatasBean item) {
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.binding.tvIntegral.setText(String.valueOf(item.getCoinCount()));
                viewHolder.binding.tvName.setText(item.getUsername());
                viewHolder.binding.tvRanking.setBackground(null);
                viewHolder.binding.tvRankingText.setText("");
                if (item.getRank().equals("1")) {
                    viewHolder.binding.tvRanking.setVisibility(View.VISIBLE);
                    viewHolder.binding.tvRankingText.setVisibility(View.GONE);
                    viewHolder.binding.tvRanking.setBackgroundResource(R.mipmap.ic_rank_1);
                    maxCount = item.getCoinCount();
                } else if (item.getRank().equals("2")) {
                    viewHolder.binding.tvRanking.setVisibility(View.VISIBLE);
                    viewHolder.binding.tvRankingText.setVisibility(View.GONE);
                    viewHolder.binding.tvRanking.setBackgroundResource(R.mipmap.ic_rank_2);
                } else if (item.getRank().equals("3")) {
                    viewHolder.binding.tvRanking.setVisibility(View.VISIBLE);
                    viewHolder.binding.tvRankingText.setVisibility(View.GONE);
                    viewHolder.binding.tvRanking.setBackgroundResource(R.mipmap.ic_rank_3);
                } else {
                    viewHolder.binding.tvRanking.setVisibility(View.GONE);
                    viewHolder.binding.tvRankingText.setVisibility(View.VISIBLE);
                    viewHolder.binding.tvRankingText.setText(item.getRank());
                }
                if (maxCount == 0) {
                    return;
                }
                viewHolder.binding.rllMain.setPercentWidth(item.getCoinCount() * 1.0f / maxCount)
                        .start();
            }
        };
        commonAdapter.setOnItemClickListener(new ComViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(RecyclerView.VERTICAL);
        bd.baseRecycle.setLayoutManager(llm);
        bd.baseRecycle.setAdapter(commonAdapter);
    }

    static class ViewHolder extends ComViewHolder {
        ItemRankListBinding binding;

        public ViewHolder(ItemRankListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    private void refresh() {
        //下拉刷新
//        bd.baseRefresh.setRefreshHeader(new ClassicsHeader(mContext));
        bd.baseRefresh.setRefreshFooter(new ClassicsFooter(mContext));
        //为下来刷新添加事件
        bd.baseRefresh.setEnableRefresh(false);
        //为上拉加载添加事件
        bd.baseRefresh.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getRankList(false);
        });
    }

    private void getRankList(boolean isLoad) {
        if (isLoad) {
            showLoading("加载中...");
        }
        presenter.getRankList(page, new WanObserver2<WanData2<RankListBean>>() {
            @Override
            public void onSuccess(WanData2<RankListBean> o) {
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