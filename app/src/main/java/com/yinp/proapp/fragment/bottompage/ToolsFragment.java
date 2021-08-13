package com.yinp.proapp.fragment.bottompage;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yinp.proapp.R;
import com.yinp.proapp.adapter.ComViewHolder;
import com.yinp.proapp.adapter.CommonAdapter;
import com.yinp.proapp.base.fragment.AppBaseFragment;
import com.yinp.proapp.bean.ToolsTopBean;
import com.yinp.proapp.databinding.FragmentToolsBinding;
import com.yinp.proapp.databinding.ItemToolsTopListBinding;
import com.yinp.proapp.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class ToolsFragment extends AppBaseFragment<FragmentToolsBinding> {
    @Override
    protected void initViews(View view) {
        initRecycler();
    }

    private List<ToolsTopBean> topList = new ArrayList<>();
    private CommonAdapter<ToolsTopBean> topAdapter;

    private void initRecycler() {
        topList.add(new ToolsTopBean("百度地图", R.mipmap.map));
        topList.add(new ToolsTopBean("百度地图", R.mipmap.map));
        topList.add(new ToolsTopBean("百度地图", R.mipmap.map));
        topAdapter = new CommonAdapter<ToolsTopBean>(getContext(), topList) {
            @Override
            protected ComViewHolder setComViewHolder(View view, int viewType, ViewGroup parent) {
                return new ToolsTopViewHolder(ItemToolsTopListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }

            @Override
            public void onBindItem(RecyclerView.ViewHolder holder, int position, ToolsTopBean item) {
                ToolsTopViewHolder viewHolder = (ToolsTopViewHolder) holder;
                if (item.getDrawableId() == -1) {
                    viewHolder.binding.ivImg.setBackgroundResource(R.mipmap.default1);
                } else {
                    viewHolder.binding.ivImg.setBackgroundResource(item.getDrawableId());
                }
                viewHolder.binding.tvTitle.setText(item.getTitle());
            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        bd.rvList.setLayoutManager(gridLayoutManager);
        bd.rvList.addItemDecoration(new SpaceItemDecoration(getContext(), 4));
        bd.rvList.setAdapter(topAdapter);
    }

    static class ToolsTopViewHolder extends ComViewHolder {
        ItemToolsTopListBinding binding;

        public ToolsTopViewHolder(ItemToolsTopListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int space;  //位移间距
        private Context context;

        public SpaceItemDecoration(Context context, int space) {
            this.space = space;
            this.context = context;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) % space == 1) {
                outRect.left = AppUtils.dpToPx(context, 20);
                outRect.right = AppUtils.dpToPx(context, 30);
            } else {
                outRect.left = AppUtils.dpToPx(context, 30);
                outRect.right = AppUtils.dpToPx(context, 20);
            }
        }

    }
}
