package com.example.a84640.clockingin.bean;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * @auther jixiang
 * @date 2019/3/10
 */
public class Tools {
    private int mimage;
    private String mTitle;


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


    public int getMimage() {
        return mimage;
    }

    public void setMimage(int mimage) {
        this.mimage = mimage;
    }

    public interface OnItemClickListener{
        void onItemClickListener(int position, List<Tools> tools);
    }

}
