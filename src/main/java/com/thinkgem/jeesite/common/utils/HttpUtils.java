package com.thinkgem.jeesite.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by tr on 2016/9/30.
 */
public class HttpUtils {
    /*
    get
     */
    public static String sendGet(String url,String parm){
        String result = "";
        String sendurl = url+"?"+parm;
        BufferedReader in=null;
        try{
            //生成url
            URL realurl = new URL(sendurl);
            //建立链接对象
            URLConnection connection = realurl.openConnection();
            //设置请求头
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //建立链接
            connection.connect();

            //获取响应头
            Map<String,List<String>> httpResponseHeader = connection.getHeaderFields();
            //打印响应头
            Iterator<Map.Entry<String,List<String>>> iterator = httpResponseHeader.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String ,List<String>> en = iterator.next();
                for(String value : en.getValue()){
                    System.out.println(en.getKey()+":"+value);
                }
            }

            //获取返回流
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line="";
            while ((line=in.readLine())!=null){
                line = new String(line.getBytes("UTF-8"),"UTF-8");
                result += line;
            }


        }catch (Exception e){
            System.out.println("url生成错误！");
            e.printStackTrace();
        }finally {
            //关闭流
            //关闭流
            try{
                if(in!=null){
                    in.close();
                }
            }catch (Exception e){
                e.printStackTrace();;
            }
        }

        return result;
    }

    //发起POST请求
    /*
     *@param parm=value1&parm=value2
     *@param url
     */
    public static String sendPost(String url,String parm){
        PrintWriter printWriter = null; //声明输出流对象
        BufferedReader in =null; //声明输入流
        String result = "";

        try{
            URL realurl = new URL(url);
            //创建链接对象
            URLConnection connection = realurl.openConnection();
            //设置响应头
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);

            //获得对应链接的输出流
            printWriter = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            printWriter.println(parm);
            //缓冲
            printWriter.flush();

            //得到响应头
            Map<String,List<String>> httpResponseHeader = connection.getHeaderFields();
            Iterator<Map.Entry<String,List<String>>> iterator = httpResponseHeader.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String ,List<String>> en = iterator.next();
                for(String value : en.getValue()){
                    System.out.println(en.getKey()+":"+value);
                }
            }

            //得到响应流
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line ="";
            while ((line=in.readLine())!=null) {
                line = new String(line.getBytes("UTF-8"),"UTF-8");
                result += line;
            }

        }catch (Exception e){
            System.out.println("url生成失败");
            e.printStackTrace();
        }
        return result;
    }
}