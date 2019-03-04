package com.example.a84640.clockingin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a84640.clockingin.R;

/**
 * 用于：当用户第一次打开时 或
 *       点击右上角帮助按钮时候
 *       的帮助界面
 * @author
 * @date 2019/3/4
 */
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }
}
