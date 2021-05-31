package com.yinp.proapp.module.wanandroid.activity;

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
import com.yinp.proapp.base.activity.PresenterBaseFragmentActivity;
import com.yinp.proapp.databinding.ActivityWanCollectionBinding;
import com.yinp.proapp.databinding.ItemWanCollectListBinding;
import com.yinp.proapp.databinding.ItemWanHomeListBinding;
import com.yinp.proapp.module.wanandroid.WanManager;
import com.yinp.proapp.module.wanandroid.bean.WanHomeListBean;
import com.yinp.proapp.module.wanandroid.fragment.WanHomeFragment;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanData;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanObserver;
import com.yinp.proapp.utils.JumpWebUtils;
import com.yinp.proapp.utils.StatusBarUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WanCollectionActivity extends PresenterBaseFragmentActivity<ActivityWanCollectionBinding, WanManager> {
    private List<WanHomeListBean> dataList = new ArrayList<>();
    private CommonAdapter<WanHomeListBean> commonAdapter;

    private int size = 10;
    private int page = 1;
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
        initRecycler();
        refresh();
    }
    private void initRecycler() {
        commonAdapter = new CommonAdapter<WanHomeListBean>(mContext, dataList) {
            @Override
            protected ComViewHolder setComViewHolder(View view, int viewType, ViewGroup parent) {
                return new ViewHolder(ItemWanCollectListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }

            @Override
            public void onBindItem(RecyclerView.ViewHolder holder, int position, WanHomeListBean item) {
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.binding.tvTitle.setText(item.getTitle());
                viewHolder.binding.tvAuthor.setText("作者：" + (TextUtils.isEmpty(item.getAuthor()) ? item.getShareUser() : item.getAuthor()));
                viewHolder.binding.tvType.setText("分类：" + item.getSuperChapterName() + "/" + item.getChapterName());
                if (item.isCollect()){

                }
            }
        };
        commonAdapter.setOnItemClickListener(new ComViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                WanHomeListBean item = dataList.get(position);
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

    private void refresh() {
        //下拉刷新
        bd.baseRefresh.setRefreshHeader(new ClassicsHeader(mContext));
        bd.baseRefresh.setRefreshFooter(new ClassicsFooter(mContext));
        //为下来刷新添加事件
        bd.baseRefresh.setOnRefreshListener(refreshlayout -> {
            page = 1;
            getCollectInfo();
        });
        //为上拉加载添加事件
        bd.baseRefresh.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getCollectInfo();
        });
    }
    /**
     * 获取首页得数据
     */
    private void getCollectInfo() {
        presenter.getHomeList(size * page, new WanObserver<WanData>() {
            @Override
            public void onSuccess(WanData o) {
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
                    dataList.addAll(arrayList);
                    bd.baseRefresh.finishRefresh();
                } else {
                    dataList.addAll(arrayList);
                    bd.baseRefresh.finishLoadMore();
                }
                bd.bottom.noLl.setVisibility(View.GONE);
                bd.baseRefresh.setVisibility(View.VISIBLE);
                commonAdapter.notifyDataSetChanged();
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