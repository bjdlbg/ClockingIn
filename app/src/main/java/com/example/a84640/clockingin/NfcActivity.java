package com.example.a84640.clockingin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a84640.clockingin.activity.HelpActivity;
import com.example.a84640.clockingin.fragment.DataFragment;
import com.example.a84640.clockingin.fragment.NfcFragment;
import com.example.a84640.clockingin.fragment.ToolsFragment;

import java.util.zip.DataFormatException;

/**
 * @author jixiang
 * @date 2019/3/3
 */
public class NfcActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    // public static final String NFC_id = "nfc_id";
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    /**
     * 底部导航栏
     * */
    private BottomNavigationView mNavigationView;
    private ViewPager mViewPager;
    private NfcFragment mNfcFragment = new NfcFragment();
    private DataFragment mDataFragment = new DataFragment();
    private ToolsFragment mToolsFragment = new ToolsFragment();
    private static Context sContext;

    /**
     * 底部导航栏的点击监听接口的实现
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_nfc:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.navigation_chart:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.navigation_tools:
                    mViewPager.setCurrentItem(2);
                    break;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.main_anctivity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_help:
                //启用帮助界面
                Intent intent=new Intent(this,HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.main_menu_der:
                //TODO:添加手机界面反转
                Toast.makeText(sContext,"已经横屏显示",Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_menu_bgc:
                //TODO:添加手机主题切换
                Toast.makeText(sContext,"请选择手机主题",Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_menu_about:
                //TODO:添加关于app的开发信息
                Toast.makeText(sContext,"显示开发信息",Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_menu_esc:
                //TODO:退出app
                Toast.makeText(sContext,"退出app",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        //加载布局
        initView();
        sContext = getApplicationContext();

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        Log.d("change pager", "getItem: return frga1");
                        return mNfcFragment;
                    case 1:
                        Log.d("change pager", "getItem: return frga2");
                        return mDataFragment;
                    case 2:
                        Log.d("change pager", "getItem: return frga3");
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
//        //设置默认进入的界面
//        sp = PreferenceManager.getDefaultSharedPreferences(this);
//        boolean isFirstInstall = sp.getBoolean("first_install", true);
//        if (isFirstInstall) {
//            mViewPager.setCurrentItem(0);
//        } else {
//            mViewPager.setCurrentItem(1);
//        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
