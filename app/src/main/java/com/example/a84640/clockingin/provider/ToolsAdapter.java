package com.example.a84640.clockingin.provider;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a84640.clockingin.R;
import com.example.a84640.clockingin.bean.Tools;

import java.util.List;

/**
 * 工具界面适配器
 * @author jixiang
 * @date 2019/3/10
 */
public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ViewHolder> {
    private List<Tools> mTools;
    private Tools.OnItemClickListener mOnItemClickListener;

    public ToolsAdapter(List<Tools> tools){
        this.mTools=tools;
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
        final Tools tools=mTools.get(viewHolder.getAdapterPosition());
        //绑定图片和标题
        viewHolder.title.setText(tools.getTitle());
        viewHolder.mImageView.setImageResource(tools.getMimage());
        //实现item点击事件接口
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(viewHolder.getAdapterPosition(),mTools);
            }
        });
    }

    /**实现item点击事件接口*/
    public void setOnItemClickListener(Tools.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener =  onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mTools.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView title;
        public final ImageView mImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.tools_title);
            mImageView=(ImageView)itemView.findViewById(R.id.tools_imageView);

        }
    }
}
