package com.example.a84640.clockingin.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.a84640.clockingin.activity.NfcActivity;
import com.example.a84640.clockingin.R;
import com.example.a84640.clockingin.bean.TeacherClass;
import com.example.a84640.clockingin.provider.ClassAdapter;
import com.example.a84640.clockingin.utilities.NetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jixiang
 * @date 2019/3/3
 */
public class TeacherFragment extends Fragment {

    private NfcActivity mNfcActivity;
    private View rootView;
    private List<TeacherClass> mTeacherClasses =new ArrayList<>();
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

        mClassAdapter=new ClassAdapter(mTeacherClasses);

        //上课列表绑定点击事件
        mClassAdapter.setItemClickListener(new ClassAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                View layout=(View)mRecyclerView.getChildAt(position);
                final TextView mClssName=layout.findViewById(R.id.class_name);
                final TextView mStuNumber=layout.findViewById(R.id.class_stu_number);
                updateStudent(mClssName.getText().toString(),mStuNumber.getText().toString());
                //Toast.makeText(getContext(),"点击item"+mClssName.getText().toString(),Toast.LENGTH_SHORT).show();

            }
        });

        //长按事件
        mClassAdapter.setItemLongClickListener(new ClassAdapter.onItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
//                mSharedPreferences=mTeacherClasses.get(position).getStudentSp();
//                final SharedPreferences.Editor editor=mSharedPreferences.edit();
//                //根据名字取出sp
//                final String key=mTeacherClasses.get(position).getClassMessage();
//                Log.d("teacherfragment","click the item"+key);
                //String value=mSharedPreferences.getString(key,"");
//                String message="";
//                if (value.equals("")){
//                    message="当前课程还没有学生";
//                }else {
//                    message="当前的课程学生名单为"+value;
//                }
                //TODO:当前弹框么有学生
//                Toast.makeText(getContext(),"当前sp么有内容",Toast.LENGTH_SHORT).show();
//                AlertDialog dialog=new AlertDialog.Builder(getContext()).setTitle("提示").setMessage(message)
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        })
//                        .setPositiveButton("添加", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //显示添加学生dialog
//                                addStudentDialog(key);
//                                dialog.dismiss();
//                            }
//                        }).create();
//                dialog.show();

            }
        });
        mRecyclerView.setAdapter(mClassAdapter);
        mClassAdapter.notifyAdapter(mTeacherClasses,false);
        return rootView;
    }

    /**
     * 通知nfcFragment更新界面，刷新rv
     * @param className
     * @param studentNum
     */
    private void updateStudent(String className,String studentNum) {
        //通知主activity更新界面
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putString("class_name",className);
        bundle.putString("class_stu_number",studentNum);
        intent.putExtras(bundle);
        intent.setAction("com.example.a84640.clockingin.teacherfragment");
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        Toast.makeText(getActivity(),"发送了一条广播",Toast.LENGTH_SHORT).show();
        //TODO:刷新rv
    }

    /**
     * 载入控件
     * @param rootView
     */
    private void initView(View rootView) {
        mClassAdapter=new ClassAdapter(mTeacherClasses);
        mRecyclerView=(RecyclerView)rootView.findViewById(R.id.recycle_teacher);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mTextViewDate=(TextView)rootView.findViewById(R.id.textView_time);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("teacher", "teacher fragment is open");
        super.onCreate(savedInstanceState);

        //首先拉取教师一的信息
        MyTaskTeacherClass myTaskTeacherClass=new MyTaskTeacherClass();
        myTaskTeacherClass.execute("教师一号");
    }

    @Override
    public void onResume() {
        super.onResume();
        initView(rootView);
    }

    /**
     * 载入程普课表信息（已经使用server端数据来代替了）
     */
//    private void initMessage(){
////        List list=new ArrayList();
////        list.add(0,"周二上午一二节");
////        list.add(1,"周二上午三四节");
////        list.add(2,"周三上午一二节");
////        list.add(3,"周三上午三四节");
////        list.add(4,"周二下午一二节");
//       // List list=getStudentListByTeacherName();
//        for (int i=0;i<=4;i++){
//            TeacherClass teacherClass=new TeacherClass();
//            //teacherClass.setClassMessage(list.get(i).toString());
//            //teacherClass.setStudentSp(this.getActivity().getSharedPreferences(list.get(i).toString(),0));
//            teacherClass.setHaveDone(false);
//            mTeacherClasses.add(teacherClass);
//        }
//    }
//

    /**
     * 获取老师列表
     * json解析为list
     * @param teacherName
     */
    public List getStudentListByTeacherName(String teacherName){
        String json=NetUtils.getClassFromServer(teacherName,"http://192.168.43.75:8080/selectClassByTeacherName");
        List list=new ArrayList();
        Log.d("json debug","从server获取数据"+json);
        try {
            JSONArray jsonArray=new JSONArray(json);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String className=jsonObject.optString("Tclass",null);
                //调试
                Log.d("json debug","收到的数据"+i+className);
                list.add(i,className);
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return list;

    }

    /**
     *  添加学生dialog
     * @param key
     */
    private void addStudentDialog(final String key){
        //TODO：获取学生列表
        final String items[] = {"陈旗开", "姬翔", "卢亮亮", "徐杨梦","灭霸"};
        final boolean checkedItems[] = {true, false, false, false,false};
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                // .setIcon(R.mipmap.icon)//设置标题的图片
                .setTitle("选择要添加的学生")//设置对话框的标题
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedItems[which] = isChecked;
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            if (checkedItems[i]) {
//                                //将学生名字存入sp
//                                SharedPreferences sharedPreferences=getContext().getSharedPreferences(key,0);
//                                SharedPreferences.Editor editor=sharedPreferences.edit();
//                                editor.putString(key,items[i]+",");
//                                editor.apply();

                            }
                        }
                        //Log.d("show sp value","当前sp的内容为"+mSharedPreferences.getString(key,""));
                        dialog.dismiss();
                    }

                }).create();
        dialog.show();
    }

    /**
     * 请求教师列表的线程
     * TODO：添加progressBAr
     */
    public class MyTaskTeacherClass extends AsyncTask<String,Void,List>{


        @Override
        protected List doInBackground(String... strings) {
            String teacherName=strings[0];//参数列表
            List list=getStudentListByTeacherName(teacherName);
            if (list==null){
                return null;
            }
            return list;
        }

        /**
         * 执行ui线程
         * @param list
         */
        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            for (int i=0;i<list.size();i++){
                TeacherClass teacherClass=new TeacherClass();
                teacherClass.setClassMessage(list.get(i).toString());
                //teacherClass.setStudentSp(this.getActivity().getSharedPreferences(list.get(i).toString(),0));
                teacherClass.setHaveDone(false);//默认没有上过
                mTeacherClasses.add(teacherClass);
            }
            //刷新适配器
            mClassAdapter.notifyAdapter(mTeacherClasses,false);

        }
    }
}
