package com.example.a84640.clockingin.utilities;

import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 网络工具类
 * @author jixiang
 * @date 2019/4/28
 */
public abstract class NetUtils {

    /**
     * 测试 ("/liaoxianghe")端口
     * 发送一段带有年龄，身高的请求
     * @param high  身高
     * @param age   年龄
     * @param url   IP地址
     * @return 返回带有参数内容的字符串信息
     */
    public static String LoginByPost(String high, String age,String url) {
        String msg = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            // 设置请求方式,请求超时信息
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            // 设置运行输入,输出:
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // Post方式不能缓存,需手动设置为false
            conn.setUseCaches(false);
            // 我们请求的数据:
            String data = "high=" + URLEncoder.encode(high, "UTF-8") + "&age=" + URLEncoder.encode(age, "UTF-8");
            // 这里可以写一些请求头的东东...
            // 获取输出流
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            if (conn.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                message.close();
                // 返回字符串
                msg = new String(message.toByteArray());
                return msg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }


    /**
     * 测试（"/setText"）
     * 向服务端发送一条文本
     * @param text
     * @param url
     * @return 请求到的一条数据（"发送成功"）
     */
    public static String sendTextToServer(String text,String url){
        String msg = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            // 设置请求方式,请求超时信息
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            // 设置运行输入,输出:
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // Post方式不能缓存,需手动设置为false
            conn.setUseCaches(false);
            // 我们请求的数据:
            String data = "msg=" + URLEncoder.encode(text, "UTF-8") ;
            // 这里可以写一些请求头的东东...
            // 获取输出流
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            if (conn.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                message.close();
                // 返回字符串
                msg = new String(message.toByteArray());
                return msg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }
}
