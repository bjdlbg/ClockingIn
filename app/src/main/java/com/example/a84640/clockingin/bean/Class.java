package com.example.a84640.clockingin.bean;

/**
 * @author jixiang
 * @date 2019/4/25
 */
public class Class {
    private String classMessage;
    private boolean haveDone;
    private long classDate;

    public String getClassMessage() {
        return classMessage;
    }

    public void setClassMessage(String classMessage) {
        this.classMessage = classMessage;
    }

    public boolean isHaveDone() {
        return haveDone;
    }

    public void setHaveDone(boolean haveDone) {
        this.haveDone = haveDone;
    }

    public long getClassDate() {
        return classDate;
    }

    public void setClassDate(long classDate) {
        this.classDate = classDate;
    }

    /**
     * 课表点击事件
     */
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
