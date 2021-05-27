package com.yinp.proapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yinp.proapp.bean.BannerEntity;
import com.yinp.proapp.databinding.ItemHomeBannerBinding;
import com.yinp.proapp.utils.GlideUtils;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class HomeBannerAdapter extends BannerAdapter<BannerEntity, HomeBannerAdapter.HomeBannerViewHolder> {

    private final Context context;

    public HomeBannerAdapter(List<BannerEntity> datas, Context context) {
        super(datas);
        this.context = context;
    }

    @Override
    public HomeBannerAdapter.HomeBannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ItemHomeBannerBinding bannerBinding = ItemHomeBannerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HomeBannerViewHolder(bannerBinding);
    }

    @Override
    public void onBindView(HomeBannerViewHolder holder, BannerEntity data, int position, int size) {
        BannerEntity entity = mDatas.get(position);

        GlideUtils.loadUrl(context, holder.bind.ivBanner, null, entity.getImagePath(), null, null, false, true);
    }

    class HomeBannerViewHolder extends RecyclerView.ViewHolder {
        public ItemHomeBannerBinding bind;

        public HomeBannerViewHolder(@NonNull ItemHomeBannerBinding bannerBinding) {
            super(bannerBinding.getRoot());
            bind = bannerBinding;
        }
    }
}
