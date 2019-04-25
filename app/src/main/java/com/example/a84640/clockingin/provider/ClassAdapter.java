package com.example.a84640.clockingin.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a84640.clockingin.R;
import com.example.a84640.clockingin.bean.Class;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jixiang
 * @date 2019/4/25
 */
public class ClassAdapter extends RecyclerView.Adapter implements Class.OnItemClickListener {
    private static final int IS_NOT_DONE=0;
    int mClassStatus=IS_NOT_DONE;
//    private Context context;
    private List<Class> mClasses=new ArrayList<>();

//    public ClassAdapter(Context context) {
//        this.context = context;
//    }

    public ClassAdapter(List<Class> classes){
        this.mClasses=classes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_class,viewGroup,false);
        final StudentAdapter.ViewHolder holder=new StudentAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mClasses.size();
    }

    /**
     * 课表item点击事件
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        //TODO:点击item跳转到第一个界面（需要传值）
    }

    /**刷新适配器*/
    public void notifyAdapter(List<Class> photoImageList, boolean isAdd) {
        if (!isAdd) {
            this.mClasses = photoImageList;
        } else {
            this.mClasses.addAll(photoImageList);
        }
        notifyDataSetChanged();
    }
}
