package com.example.a84640.clockingin.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a84640.clockingin.R;
import com.example.a84640.clockingin.utilities.NetUtils;

/**
 * app入口
 */
public class LoginActivity extends AppCompatActivity {

    private Button mButton;
    private Spinner mSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSpinner=(Spinner)findViewById(R.id.spinner1);

        mButton=(Button)findViewById(R.id.button_login);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkServer()){
                    Toast.makeText(getApplication(),"连接成功",Toast.LENGTH_SHORT).show();
                    goToMain();//登陆主界面
                }else {
                    Toast.makeText(getApplication(),"访问server失败",Toast.LENGTH_SHORT).show();
                }

            }
        });

        //长按可以跳过server端检测（测试方便）
        mButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                goToMain();
                return true;
            }
        });


    }

    /**
     * 登陆主界面
     */
    private void goToMain(){
        Intent intent=new Intent(LoginActivity.this,NfcActivity.class);
        intent.putExtra("teacher_nemr",mSpinner.getSelectedItem().toString());
        Log.d("loginactivity",mSpinner.getSelectedItem().toString());
        startActivity(intent);
    }

    /**
     * 测试是否连接成功
     * @return
     */
    private boolean checkServer(){
        MyTaskLogin myTask=new MyTaskLogin();
        if (myTask.execute().equals("连接成功")){
            return true;
        }
        return false;
    }

    private class MyTaskLogin extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String s=NetUtils.uniMethodSetOneStringParam(null,null,"http://192.168.43.75:8080/testServer");
            return s;
        }
    }

}
