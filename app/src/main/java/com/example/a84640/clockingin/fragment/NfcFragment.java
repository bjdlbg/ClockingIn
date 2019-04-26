package com.example.a84640.clockingin.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.a84640.clockingin.R;
import com.example.a84640.clockingin.bean.StudentInfo;
import com.example.a84640.clockingin.provider.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 第一个界面，用来刷卡，读卡，学生信息比对
 * @author jixiang
 * @date 2019/3/3
 */
public class NfcFragment extends Fragment implements StudentInfo.OnItemClickListener {

    public View rootView;
    private Button mButtonRefuse;
    private Button mButtonNext;
    private RecyclerView mRecyclerView;
    private StudentAdapter mStudentAdapter=null;
    private List<StudentInfo> mStudentInfoList=new ArrayList<>();
    public static LinearLayout linearLayout;

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
     *
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


}
