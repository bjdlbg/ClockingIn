package com.example.a84640.clockingin.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a84640.clockingin.R;
import com.example.a84640.clockingin.bean.StudentInfo;

import java.util.List;

/**
 * 学生列表适配器
 * @author jixiang
 * @date 2019/3/10
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private List<StudentInfo> mStudentInfoList;
    private StudentInfo.OnItemClickListener mOnItemClickListener;

    public StudentAdapter(List<StudentInfo> studentInfos){
        this.mStudentInfoList =studentInfos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tools,
                viewGroup,false);
        final ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final StudentInfo studentInfo= mStudentInfoList.get(viewHolder.getAdapterPosition());
        //绑定图片和标题
        viewHolder.title.setText(studentInfo.getStudentName());
        viewHolder.mImageView.setImageResource(studentInfo.getStudentImage());
        //实现item点击事件接口
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(viewHolder.getAdapterPosition(), mStudentInfoList);
            }
        });
    }

    /**实现item点击事件接口*/
    public void setOnItemClickListener(StudentInfo.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener =  onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mStudentInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView title;
        final ImageView mImageView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.tools_title);
            mImageView=(ImageView)itemView.findViewById(R.id.tools_imageView);

        }
    }

    /**刷新适配器*/
    public void notifyAdapter(List<StudentInfo> studentInfos, boolean isAttent) {
        if (!isAttent) {
            this.mStudentInfoList = studentInfos;
        } else {
            this.mStudentInfoList.addAll(studentInfos);
        }
        notifyDataSetChanged();
    }
}
