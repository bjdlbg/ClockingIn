package com.example.a84640.clockingin.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a84640.clockingin.R;
import com.example.a84640.clockingin.bean.TeacherClass;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jixiang
 * @date 2019/4/25
 */
public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder>{
    private static final int IS_NOT_DONE=0;
    int mClassStatus=IS_NOT_DONE;
    private Context context;
    private List<TeacherClass> mTeacherClasses =new ArrayList<>();
    private onItemClickListener onItemClickListener;
    private onItemLongClickListener onItemLongClickListener;

    public ClassAdapter(Context context) {
        this.context = context;
    }

    public ClassAdapter(List<TeacherClass> teacherClasses){
        this.mTeacherClasses = teacherClasses;
    }

    public void setItemClickListener(onItemClickListener mItemClickListener) {
        onItemClickListener = mItemClickListener;
    }

    public void setItemLongClickListener(onItemLongClickListener mItemLongClickListener) {
        onItemLongClickListener = mItemLongClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (context==null){
            context=viewGroup.getContext();
        }
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_class,viewGroup,false);
        final ClassAdapter.ViewHolder holder=new ClassAdapter.ViewHolder(view);
        //绑定点击事件接口
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });

        //长按接口
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = holder.getAdapterPosition();
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(v, position);
                }
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TeacherClass teacherClass=mTeacherClasses.get(i);
        //根据上课状态设置res
       if (!teacherClass.isHaveDone()){
           viewHolder.isDone.setImageResource(R.drawable.unselect);
       }else {
           viewHolder.isDone.setImageResource(R.drawable.select);
       }
        viewHolder.techerClass.setText(teacherClass.getClassMessage());
    }


    @Override
    public int getItemCount() {
        return mTeacherClasses.size();
    }


    /**刷新适配器*/
    public void notifyAdapter(List<TeacherClass> photoImageList, boolean isAdd) {
        if (!isAdd) {
            this.mTeacherClasses = photoImageList;
        } else {
            this.mTeacherClasses.addAll(photoImageList);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView techerClass;
        public ImageView isDone;

        public ViewHolder(View itemView) {
            super(itemView);
            techerClass = (TextView) itemView.findViewById(R.id.class_name);
            isDone = (ImageView) itemView.findViewById(R.id.pic_class_whether_done);
        }
    }

    /**
     * 设置监听接口
     */
    public interface onItemClickListener {
        /**
         * @param view
         * @param position
         */
        public void onItemClick(View view, int position);

    }

    /**
     * 长按监听接口
     */
    public interface onItemLongClickListener {
        public void onItemLongClick(View view, int position);
    }

}
