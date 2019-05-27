package com.example.a84640.clockingin.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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
import com.example.a84640.clockingin.activity.NfcActivity;
import com.example.a84640.clockingin.bean.StudentInfo;
import com.example.a84640.clockingin.provider.StudentAdapter;
import com.example.a84640.clockingin.utilities.NetUtils;
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

import static com.example.a84640.clockingin.activity.NfcActivity.IP_NUM;
import static com.example.a84640.clockingin.activity.NfcActivity.WEEK_NUM;
import static com.example.a84640.clockingin.utilities.NetUtils.LoginByPost;

/**
 * 第一个界面，用来刷卡，读卡，学生信息比对，签到。
 * @author jixiang
 * @date 2019/3/3
 */
public class NfcFragment extends Fragment implements StudentInfo.OnItemClickListener,StudentInfo.OnItemLongClickListenner{

    public View rootView;
    private Button mButtonRefuse;
    private Button mButtonNext;
    private RecyclerView mRecyclerView;
    private StudentAdapter mStudentAdapter=null;
    private List<StudentInfo> mStudentInfoList=new ArrayList<>();
    public static LinearLayout linearLayout;
    private TextView mTextView;
    //用于接收广播
    private LocalBroadcastManager broadcastManager;
    private IntentFilter intentFilter;
    private BroadcastReceiver mReceiver;
    private TextView mClassNumber;
    public TextView mStuNumber;
    private AlertDialog mDialog;

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
        broadcastManager=LocalBroadcastManager.getInstance(getActivity());
        intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.a84640.clockingin.teacherfragment");
        mReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                intent.getExtras();
                final Bundle bd=intent.getExtras();
                //String number=bd.getString("class_stu_number");
                String className=bd.getString("class_name");
                //收到广播跳转到第一个界面
                NfcActivity activity=(NfcActivity)getActivity();
                if (activity != null) {
                    activity.getViewPager().setCurrentItem(0);
                }
                updateStuRv(className);//获取列表
                Toast.makeText(getActivity(),"学生列表已经更新",Toast.LENGTH_SHORT).show();


            }
        };
        broadcastManager.registerReceiver(mReceiver, intentFilter);
    }

    /**
     * 调用activity中更新列表方法
     * @param
     */
    private void updateStuRv(String className) {
        NfcActivity.MyTaskClassStu myTaskClassStu=new NfcActivity.MyTaskClassStu();
        myTaskClassStu.execute("className",className);
        //mStuNumber.setText("人数："+mStudentInfoList.size());
        mClassNumber.setText("班级："+className);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    public void setStudentInfoList(List<StudentInfo> studentInfoList) {
        mStudentInfoList = studentInfoList;
        //刷新适配器
        mStudentAdapter.notifyAdapter(mStudentInfoList,false);
    }

    public List<StudentInfo> getStudentInfoList() {
        return mStudentInfoList;
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
        mStuNumber=(TextView)rootView.findViewById(R.id.tv_stu_number);
        mClassNumber=(TextView)rootView.findViewById(R.id.tv_class_number);
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
        mStudentAdapter.setOnItemLongClickListenner(this);

    }

    /**
     * 点击item弹出对应学生的dialog
     * @param position
     * @param studentInfos
     */
    @Override
    public void onItemClickListener(final int position, final List<StudentInfo> studentInfos) {
        showClockDialog(position,studentInfos);
    }

    //签到dialog（刷卡或者手动点击都会出现）
    public void showClockDialog(final int position, final List<StudentInfo> studentInfos){
        View dialog= LayoutInflater.from(getContext()).inflate(R.layout.dialog_student_message,null);
        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        final String name=studentInfos.get(position).getStudentName();
        builder.setTitle(name);
        builder.setNegativeButton("标记", new DialogInterface.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO:添加动作
                Toast.makeText(getContext(),"您标记了"+name,Toast.LENGTH_SHORT).show();
                //item添加标记状态（有bug背景传递）
                mRecyclerView.findViewHolderForLayoutPosition(position).itemView.setBackgroundColor(R.color.design_default_color_primary_dark);


            }
        });
        builder.setPositiveButton("签到", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mStudentInfoList.remove(position);
                mStudentAdapter.notifyAdapter(mStudentInfoList,false);

                //数据库更新状态
                MyWeekTask myWeekTask=new MyWeekTask();
                myWeekTask.execute(WEEK_NUM,name);
                //签到成功
                alertDialog();

            }
        });
        builder.setView(dialog);
        builder.create().show();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onItemLongClickListenner(int position) {
        mRecyclerView.findViewHolderForLayoutPosition(position).itemView.setBackgroundColor(R.color.white);
        Toast.makeText(getActivity(),"取消标记",Toast.LENGTH_SHORT).show();
        return true;
    }


    /**
     * 网络请求需要放在异步线程之中
     */
        public class MyTask extends android.os.AsyncTask<String,Void,String> {

            @Override
            protected String doInBackground(String... strings) {
                return LoginByPost("180","18",IP_NUM+"/hello");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mTextView.setText(s);
            }
        }

    private void alertDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this.getActivity());
        builder.setTitle(R.string.prompt);
        builder.setMessage(R.string.the_video_is_removed);
        builder.setNegativeButton(R.string.btn_op,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                dialog.dismiss();
                timer.cancel();//取消倒计时
            }
        });
        mDialog=builder.create();
        mDialog.show();
        timer.start();
    }

    class MyWeekTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String s=strings[0];
            String s2=strings[1];
            return NetUtils.uniMethodSetTwoStringParam("weekNum",s,"name",s2,IP_NUM+"/updateWeek");
        }
    }

    CountDownTimer timer= new CountDownTimer(3000,1000) {
        @Override
        public void onTick(long arg0) {
            int thetime=(int) (arg0/1000);
            if(mDialog!=null){
                mDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText(getString(R.string.btn_op, thetime));
            }
        }


        @Override
        public void onFinish() {
            if(mDialog!=null){
                mDialog.dismiss();
            }
        }
    };

}
