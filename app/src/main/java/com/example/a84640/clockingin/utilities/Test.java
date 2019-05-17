package com.example.a84640.clockingin.utilities;

import android.net.wifi.WifiConfiguration;

import com.example.a84640.clockingin.fragment.TeacherFragment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;



public class Test {
    public TeacherFragment mTeacherFragment;
    public static void main(String[] args) throws Exception {
        //方法一
//        try {
//            System.out.println(getURLContent());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//String urlStr = "https://192.168.1.123:8080/hello";
//getURLContent();
//       System.out.println(getURLContent(urlStr));
        //getJsonByInternet("http://192.168.1.123:8080/hello");
    //    System.out.print(LoginByPost("180","18","http://192.168.1.123:8080/liaoxianghe"));

        //NetUtils.getClassFromServer("教师一号","http://192.168.43.75:8080/selectClassByTeacherName");
        //System.out.print(NetUtils.getClassFromServer("教师一号","http://192.168.43.75:8080/selectClassByTeacherName"));

        //getStudentListByTeacherName();
        //System.out.print(selectClothes());
        System.out.print(NetUtils.uniMethodSetOneStringParam("className","周一上午一二节","http://192.168.43.75:8080/selectStuByClassName"));
    }

    public static String getURLContent() throws Exception {
        String strURL = "https://192.168.1.123:8080/hello";
        URL url = new URL(strURL);
        HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
        httpConn.setRequestMethod("GET");
        httpConn.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
        String line;
        StringBuffer buffer = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        httpConn.disconnect();

        System.out.println(buffer.toString());
        return buffer.toString();
    }

    /**
     * 程序中访问http数据接口
     */
    public static String getURLContent(String urlStr) {
        /** 网络的url地址 */
        URL url = null;
        /** http连接 */
        HttpURLConnection httpConn = null;
        /**//** 输入流 */
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(urlStr);
            in = new BufferedReader(new InputStreamReader(url.openStream(), "GBK"));
            String str = null;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception ex) {

        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
        String result = sb.toString();
        System.out.println(result);
        return result;
    }


    /**
     * 小宝贝周六穿什么粗去
     * @return
     */
    public static String selectClothes(){
        //下身衣物
        String wear[]={"长裤","短裤","长裙","短裙","下身不穿"};
        //上身衣服
        String T_shirt[]={"红短袖","黑短袖","上身不穿"};
        //胖次color
        String panci[]={"black","white","purple","blue","no_panci"};
        int id1=(int)(Math.random()*10)%5+1;
        int id2=(int)(Math.random()*10)%3+1;
        int id3=(int)(Math.random()*10)%5+1;

        return wear[id1]+"+"+T_shirt[id2]+"+"+panci[id3];
    }

}
