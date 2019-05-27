package com.example.a84640.clockingin.bean;

import java.util.List;

/**
 * 学生列表实体类
 * @auther jixiang
 * @date 2019/3/10
 */
public class StudentInfo {
    private int studentImage;//学生头像
    private String studentName;//学生名字
    private String msg;

    public int getStudentImage() {
        return studentImage;
    }

    public void setStudentImage(int studentImage) {
        this.studentImage = studentImage;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }



    public interface OnItemClickListener{
        void onItemClickListener(int position, List<StudentInfo> tools);
    }

    public interface OnItemLongClickListenner{
        boolean onItemLongClickListenner(int position);
    }

}
