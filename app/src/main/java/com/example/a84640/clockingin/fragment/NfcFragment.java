package com.example.a84640.clockingin.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a84640.clockingin.R;
import com.example.a84640.clockingin.bean.StudentInfo;
import com.example.a84640.clockingin.provider.StudentAdapter;
import com.example.a84640.clockingin.utilities.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static com.example.a84640.clockingin.utilities.NetUtils.LoginByPost;

/**
 * 第一个界面，用来刷卡，读卡，学生信息比对，签到。
 * @author jixiang
 * @date 2019/3/3
 */
public class NfcFragment extends Fragment implements StudentInfo.OnItemClickListener{

    public View rootView;
    private Button mButtonRefuse;
    private Button mButtonNext;
    private RecyclerView mRecyclerView;
    private StudentAdapter mStudentAdapter=null;
    private List<StudentInfo> mStudentInfoList=new ArrayList<>();
    public static LinearLayout linearLayout;
    private TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_nfc,container,false);
        //加载控件
        initView();
        //拒绝签到点击事件

        mButtonRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    MyTask myTask=new MyTask();
                    myTask.execute();

                Log.d("network","成功显示数据:");
            }
        });
        //下一个按钮点击事件
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setLayout();//设置列表布局
        initStudentMessage();//设置列表信息
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

    /**
     * 载入控件
     */
    public void initView(){
        mTextView=(TextView)rootView.findViewById(R.id.text_3s);
        mButtonNext=(Button) rootView.findViewById(R.id.btn_next);
        mButtonRefuse=(Button) rootView.findViewById(R.id.btn_refuse);
        mRecyclerView=(RecyclerView)rootView.findViewById(R.id.my_stu_recyclerview);
        linearLayout=(LinearLayout)rootView.findViewById(R.id.lists);
    }

    /**
     * 载入数据（暂时使用虚拟数据）
     */
    public void initStudentMessage(){
        for (int i=1;i<=7;i++){
            StudentInfo studentInfo=new StudentInfo();
            studentInfo.setStudentImage(R.drawable.student);
            studentInfo.setStudentName("张三");
            mStudentInfoList.add(studentInfo);
            mStudentAdapter.notifyAdapter(mStudentInfoList,false);
        }
    }

    /**
     * 设置适配器与列表布局
     */
    public void setLayout(){
        mStudentAdapter=new StudentAdapter(mStudentInfoList);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mStudentAdapter);
        //添加列表item的点击事件
        mStudentAdapter.setOnItemClickListener(this);

    }

    /**
     * 点击item弹出对应学生的dialog
     * @param position
     * @param studentInfos
     */
    @Override
    public void onItemClickListener(final int position, final List<StudentInfo> studentInfos) {
        View dialog= LayoutInflater.from(getContext()).inflate(R.layout.dialog_student_message,null);
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        final String name=studentInfos.get(position).getStudentName();
        builder.setTitle(name);
        builder.setNegativeButton("标记", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO:添加动作
                Toast.makeText(getContext(),"您标记了"+name,Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setView(dialog);
        builder.create().show();
    }


    /**
     * 网络请求需要放在异步线程之中
     */
        public class MyTask extends android.os.AsyncTask<String,Void,String> {

            @Override
            protected String doInBackground(String... strings) {
                return LoginByPost("180","18","http://192.168.43.75:8080/hello");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mTextView.setText(s);
            }
        }



}
