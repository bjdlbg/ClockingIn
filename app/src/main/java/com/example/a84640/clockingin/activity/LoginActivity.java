package com.example.a84640.clockingin.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a84640.clockingin.R;
import com.example.a84640.clockingin.utilities.NetUtils;

/**
 * app登陆界面
 * @author jixiang
 */
public class LoginActivity extends AppCompatActivity {

    public static String WEEK_NUM=null;
    public static String IP_NUM=null;
    public static String TEACHER_NAME=null;
    public static String LOGIN_VALUE="";
    private Button mButton;
    private Spinner mSpinner;
    private Spinner mSpinnerWeek;
    private EditText mEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //选择教师下拉框
        mSpinner=(Spinner)findViewById(R.id.spinner_teacher);
        //选择周数
        mSpinnerWeek=(Spinner)findViewById(R.id.spinner_week);
        mEditText=(EditText)findViewById(R.id.editText);


        mButton=(Button)findViewById(R.id.button_login);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkServer()){
                    Toast.makeText(getApplication(),"连接成功",Toast.LENGTH_SHORT).show();
                    goToMain();//登陆主界面
                }else {
                    Toast.makeText(getApplication(),"访问server失败,请检查ip是否正确",Toast.LENGTH_SHORT).show();
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
        WEEK_NUM=mSpinnerWeek.getSelectedItem().toString();//获取周数
        IP_NUM="http://"+mEditText.getText().toString().trim()+":8080";//获取全局ip
        TEACHER_NAME=mSpinnerWeek.getSelectedItem().toString();//当前登陆教师名字

        Intent intent=new Intent(LoginActivity.this,NfcActivity.class);
        Bundle bd=new Bundle();
        bd.putString("week_num",WEEK_NUM);
        bd.putString("ip_num",IP_NUM);
        bd.putString("teacher_name",TEACHER_NAME);
        intent.putExtras(bd);

        Log.d("loginactivity",mSpinner.getSelectedItem().toString()+"当前周数："+WEEK_NUM);
        startActivity(intent);
    }

    /**
     * 测试是否连接成功
     * @return
     */
    private boolean checkServer(){
        MyTaskLogin myTask=new MyTaskLogin();
        myTask.execute();
        Log.d("loginactivity-----",LOGIN_VALUE);
        if (LOGIN_VALUE.equals("连接成功")){
            return true;
        }
        return false;
    }


    private class MyTaskLogin extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String s=null;
            String ip=mEditText.getText().toString().trim();
            String iphost="http://"+ip+":8080/testServer";
            if (ip.equals("")){
                s="";
            }else {
                s = NetUtils.uniMethodSetOneStringParam("", "", iphost);
                Log.d("loginactivity", iphost);
            }
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            LOGIN_VALUE=s;
        }
    }

}
