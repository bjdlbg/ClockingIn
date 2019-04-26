package com.example.a84640.clockingin.fragment;

import android.content.Context;
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
import android.widget.TextView;

import com.example.a84640.clockingin.activity.NfcActivity;
import com.example.a84640.clockingin.R;
import com.example.a84640.clockingin.bean.Class;
import com.example.a84640.clockingin.provider.ClassAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jixiang
 * @date 2019/3/3
 */
public class TeacherFragment extends Fragment {

    private NfcActivity mNfcActivity;
    private View rootView;
    private List<Class> mClasses=new ArrayList<>();
    private ClassAdapter mClassAdapter;
    private TextView mTextViewDate;
    private TextView mTextViewHour;
    private RecyclerView mRecyclerView;


    /**
     * 当fragment与activity建立联系时执行此函数
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mNfcActivity = (NfcActivity) context;

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_teacher,container,false);
        //初始化控件
        initView(rootView);
        initMessage();

        mClassAdapter=new ClassAdapter(mClasses);
//        mClassAdapter.onItemClick();接口还没完成
        mRecyclerView.setAdapter(mClassAdapter);
        return rootView;
    }

    private void initView(View rootView) {
        mClassAdapter=new ClassAdapter(mClasses);
        mRecyclerView=(RecyclerView)rootView.findViewById(R.id.recycle_teacher);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("teacher", "teacher fragment is open");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        initView(rootView);
    }


    private void initMessage(){
        for (int i=1;i<=7;i++){
            // PhotoImage photoInfo=new PhotoImage("照片信息： 照片"+i+"的信息");
            Class mClassMsg=new Class();
            mClassMsg.setClassDate(System.currentTimeMillis());
            mClassMsg.setClassMessage("周三上午三四节");
            mClassMsg.setHaveDone(false);
            mClasses.add(mClassMsg);
            mClassAdapter.notifyAdapter(mClasses,false);

        }
    }

}
