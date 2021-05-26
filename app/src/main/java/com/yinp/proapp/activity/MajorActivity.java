package com.yinp.proapp.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.yinp.proapp.R;
import com.yinp.proapp.base.activity.AppBaseFragmentActivity;
import com.yinp.proapp.databinding.ActivityMajorBinding;
import com.yinp.proapp.fragment.HomeFragment;
import com.yinp.proapp.fragment.MeFragment;
import com.yinp.proapp.fragment.RecreationFragment;
import com.yinp.proapp.fragment.StudyFragment;
import com.yinp.proapp.fragment.ToolsFragment;
import com.yinp.proapp.utils.StatusBarUtil;

/**
 * 首页
 */
public class MajorActivity extends AppBaseFragmentActivity<ActivityMajorBinding> {
    Fragment curFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    HomeFragment homeFragment;
    StudyFragment studyFragment;
    ToolsFragment toolsFragment;
    RecreationFragment recreationFragment;
    MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(mContext));
    }

    @Override
    protected void initViews() {
        bd.header.headerBackImg.setVisibility(View.GONE);
        bd.header.headerCenterTitle.setText("首页");
        bd.bottomNavigationView.setSelectedItemId(R.id.one);
        bd.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.one:
                        chooseFragment(0);
                        break;
                    case R.id.two:
                        chooseFragment(1);
                        break;
                    case R.id.three:
                        chooseFragment(2);
                        break;
                    case R.id.four:
                        chooseFragment(3);
                        break;
                    case R.id.five:
                        chooseFragment(4);
                        break;
                }
                return true;
            }
        });
        bd.bottomNavigationView.setItemIconTintList(null);
        bd.bottomNavigationView.setItemTextColor(getColorStateList(R.color.selector_8a8a8a_ff4d4d));
        bd.bottomNavigationView.setItemIconSize((int) getResources().getDimension(R.dimen._28dp));
        bd.bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        fragmentManager = getSupportFragmentManager();
    }

    private void chooseFragment(int position) {
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.fl_content, homeFragment);
                }
                if (curFragment != null) {
                    fragmentTransaction.hide(curFragment);
                }
                curFragment = homeFragment;
                fragmentTransaction.show(curFragment);
                fragmentTransaction.commitNow();
                break;
            case 1:
                if (studyFragment == null) {
                    studyFragment = new StudyFragment();
                    fragmentTransaction.add(R.id.fl_content, studyFragment);
                }
                if (curFragment != null) {
                    fragmentTransaction.hide(curFragment);
                }
                curFragment = studyFragment;
                fragmentTransaction.show(curFragment);
                fragmentTransaction.commitNow();
                break;
            case 2:
                if (toolsFragment == null) {
                    toolsFragment = new ToolsFragment();
                    fragmentTransaction.add(R.id.fl_content, toolsFragment);
                }
                if (curFragment != null) {
                    fragmentTransaction.hide(curFragment);
                }
                curFragment = toolsFragment;
                fragmentTransaction.show(curFragment);
                fragmentTransaction.commitNow();
                break;
            case 3:
                if (recreationFragment == null) {
                    recreationFragment = new RecreationFragment();
                    fragmentTransaction.add(R.id.fl_content, recreationFragment);
                }
                if (curFragment != null) {
                    fragmentTransaction.hide(curFragment);
                }
                curFragment = recreationFragment;
                fragmentTransaction.show(curFragment);
                fragmentTransaction.commitNow();
                break;
            case 4:
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    fragmentTransaction.add(R.id.fl_content, meFragment);
                }
                if (curFragment != null) {
                    fragmentTransaction.hide(curFragment);
                }
                curFragment = meFragment;
                fragmentTransaction.show(curFragment);
                fragmentTransaction.commitNow();
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }
}