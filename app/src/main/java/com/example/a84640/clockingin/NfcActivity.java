package com.example.a84640.clockingin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.a84640.clockingin.fragment.DataFragment;
import com.example.a84640.clockingin.fragment.NfcFragment;
import com.example.a84640.clockingin.fragment.ToolsFragment;

import java.util.zip.DataFormatException;

public class NfcActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    // public static final String NFC_id = "nfc_id";
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private BottomNavigationView mNavigationView;
    private ViewPager mViewPager;
    private NfcFragment mNfcFragment = new NfcFragment();
    private DataFragment mDataFragment = new DataFragment();
    private ToolsFragment mToolsFragment = new ToolsFragment();
    private static Context sContext;

    /**
     * 导航栏点击事件的监听接口
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mViewPager.setCurrentItem(item.getOrder());
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        initView();
        sContext = getApplicationContext();
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i) {
                    case 0:
                        return mNfcFragment;
                    case 1:
                        return mDataFragment;
                    case 2:
                        return mToolsFragment;
                    default:
                        break;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        //设置默认进入的界面
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstInstall = sp.getBoolean("first_install", true);
        if (isFirstInstall) {
            mViewPager.setCurrentItem(0);
        } else {
            mViewPager.setCurrentItem(1);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //页面滑动时候设置导航的选中状态
        mNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public static Context getContext() {
        return sContext;
    }

    /**
     * 初始化布局
     */
    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.addOnPageChangeListener(this);
        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor = sp.edit();
        editor.putBoolean("first_install", false);
        editor.apply();
    }

}