package com.example.a84640.clockingin.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a84640.clockingin.R;
import com.example.a84640.clockingin.utilities.NetUtils;

import java.util.PriorityQueue;

/**
 * 用于：当用户第一次打开时 或
 *       点击右上角帮助按钮时候
 *       的帮助界面
 * @author
 * @date 2019/3/4
 */
public class HelpActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button mButton;
    private Button mButtonOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        //输入一条信息
        mTextView=(TextView)findViewById(R.id.test_tv);
        //发送按钮
        mButton=(Button)findViewById(R.id.button_test);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用异步线程执行网络请求
                SentTextTask m=new SentTextTask();
                m.execute();
            }
        });

        //测试步进电机
        mButtonOpen=(Button)findViewById(R.id.button_turn_on);
        mButtonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    TestPortTask m=new TestPortTask();
                    m.execute();
            }
        });

    }

    /**
     * 向服务器发送一条数据服务器
     */
    private String testServer() {
        String message=mTextView.getText().toString().trim();
        if ("发送成功".equals(NetUtils.sendTextToServer(message, "http://192.168.43.75:8080/setText"))){
            //Toast.makeText(this,"数据发送成功",Toast.LENGTH_SHORT).show();
            return "数据发送成功";
        }else {
            return "发送失败";
            //Toast.makeText(this,"发送失败",Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressLint("StaticFieldLeak")
    public class SentTextTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... strings) {
            return testServer();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    /**
     * 测试用，帮助界面点击左侧按钮，测试步进电机
     */
    @SuppressLint("StaticFieldLeak")
    public class TestPortTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            return NetUtils.sendTextToServer("", "http://192.168.43.75:8080/testPort");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


}
