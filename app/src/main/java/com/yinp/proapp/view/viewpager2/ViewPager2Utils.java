package com.yinp.proapp.view.viewpager2;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.List;

public class ViewPager2Utils {
    public static void bind(final MagicIndicator magicIndicator, ViewPager2 viewPager) {
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                magicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }
        });
    }

    public static void unBind(ViewPager2 viewPager) {
        viewPager.unregisterOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    public static FragmentStateAdapter getAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments) {
        return new MFragmentStateAdapter(fragmentActivity, fragments, -1);
    }

    public static FragmentStateAdapter getAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments, int position) {
        return new MFragmentStateAdapter(fragmentActivity, fragments, position);
    }

    public static FragmentStateAdapter getAdapter(@NonNull Fragment fragment, List<Fragment> fragments) {
        return new MFragmentStateAdapter(fragment, fragments);
    }

    public static FragmentStateAdapter getAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> fragments) {
        return new MFragmentStateAdapter(fragmentManager, lifecycle, fragments);
    }

    static class MFragmentStateAdapter extends FragmentStateAdapter {
        private List<Fragment> fragments;

        public MFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments, int position) {
            super(fragmentActivity);
            this.fragments = fragments;
        }

        public MFragmentStateAdapter(@NonNull Fragment fragment, List<Fragment> fragments) {
            super(fragment);
            this.fragments = fragments;
        }

        public MFragmentStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> fragments) {
            super(fragmentManager, lifecycle);
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
//            return ClientFiveRecordsListFragment.newInstance(position);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }

    }

    public static FragmentStateAdapter getAdapter(@NonNull FragmentActivity fragmentActivity, SparseArray<Fragment> fragments) {
        return new MFragmentStateAdapter2(fragmentActivity, fragments, -1);
    }

    public static FragmentStateAdapter getAdapter(@NonNull FragmentActivity fragmentActivity, SparseArray<Fragment> fragments, int position) {
        return new MFragmentStateAdapter2(fragmentActivity, fragments, position);
    }

    public static FragmentStateAdapter getAdapter(@NonNull Fragment fragment, SparseArray<Fragment> fragments) {
        return new MFragmentStateAdapter2(fragment, fragments);
    }

    public static FragmentStateAdapter getAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, SparseArray<Fragment> fragments) {
        return new MFragmentStateAdapter2(fragmentManager, lifecycle, fragments);
    }

    static class MFragmentStateAdapter2 extends FragmentStateAdapter {
        private SparseArray<Fragment> fragments;

        public MFragmentStateAdapter2(@NonNull FragmentActivity fragmentActivity, SparseArray<Fragment> fragments, int position) {
            super(fragmentActivity);
            this.fragments = fragments;
        }

        public MFragmentStateAdapter2(@NonNull Fragment fragment, SparseArray<Fragment> fragments) {
            super(fragment);
            this.fragments = fragments;
        }

        public MFragmentStateAdapter2(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, SparseArray<Fragment> fragments) {
            super(fragmentManager, lifecycle);
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
//            return ClientFiveRecordsListFragment.newInstance(position);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }

    }
}
