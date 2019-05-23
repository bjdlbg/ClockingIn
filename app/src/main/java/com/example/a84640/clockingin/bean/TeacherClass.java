package com.example.a84640.clockingin.bean;

import android.content.SharedPreferences;

/**
 * @author jixiang
 * @date 2019/4/25
 */
public class TeacherClass {
    private String classMessage;//课名
    private String classStuNum;//学生数量
    private boolean haveDone;//是否上过
    private long classDate;


    public String getClassStuNum() {
        return classStuNum;
    }

    public void setClassStuNum(String classStuNum) {
        this.classStuNum = classStuNum;
    }

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

}
