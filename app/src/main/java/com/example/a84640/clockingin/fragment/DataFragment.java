package com.example.a84640.clockingin.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a84640.clockingin.R;
import com.example.a84640.clockingin.bean.StudentAll;
import com.example.a84640.clockingin.utilities.NetUtils;

import java.util.List;

import static com.example.a84640.clockingin.activity.NfcActivity.IP_NUM;

/**
 * @author jixiang
 * @date 2019/3/3
 */
public class DataFragment extends Fragment {

    private View rootView;
    private TextView mTextView;
    private EditText mEdiText;
    private Button mClearButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       rootView = inflater.inflate(R.layout.fragment_chart,container,false);
       return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取到控件
        mClearButton=(Button) rootView.findViewById(R.id.btn_ns_clear);
        mTextView = (TextView) rootView.findViewById(R.id.text_show);
        mEdiText = (EditText) rootView.findViewById(R.id.edit_name);

        mClearButton.setOnClickListener(new View.OnClickListener() {
            /**
             * 点击按钮清空输入框
             * @param v
             */
            @Override
            public void onClick(View v) {
                mEdiText.getText().clear();
            }
        });


        //用户触摸EditText得到焦点
        mEdiText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    //得到焦点时显示清除按钮
                    mClearButton.setVisibility(View.VISIBLE);
                }else {
                    //失去焦点时隐藏清除按钮
                    mClearButton.setVisibility(View.GONE);
                }
            }

        });
        Button mButton = (Button) rootView.findViewById(R.id.btn_lookup);

        /**
         * 搜素按钮的点击事件
         * 点击时执行线程
         */
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=mEdiText.getText().toString().trim();
                if (name.equals("") ||
                        name.equals("输入学生名字") ) {
                    mTextView.setText("请输入学生名字");
                } else {
                    //调用线程1
                    MyLookTask nsTask=new MyLookTask();
                    nsTask.execute("name",name);

                }
            }
        });
    }



    public class MyLookTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String para=strings[0];
            String value=strings[1];
            //查询单个学生信息
            String json=NetUtils.uniMethodSetOneStringParam(para,value,IP_NUM+"/selectStudentAll");
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String value="";
            value=s.replaceAll(",","\n");
            mTextView.setText(value);
        }
    }
}
