package com.virjar.sekiro.server.taskinvoke;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

/**
 * 注册到调度服务器
 */
public class TaskInvoke implements Runnable{
    private static final String HEART="/redisProxy/heart";
    private String name;
    private String taskInvokeServerAddr;
    public TaskInvoke(String name,String taskInvokeServerAddr){
        this.name=name;
        this.taskInvokeServerAddr=taskInvokeServerAddr;
    }
    @Override
    public void run() {
        System.out.println("TaskInvoke begin "+name);
        //死循环
        while(true) {
            try {
                Thread.sleep(10*1000);
                sendHeart();
                Thread.sleep(180*1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    private void sendHeart()
    {
        try {
            String strUrl=taskInvokeServerAddr+HEART+"?gwName="+name;
            System.out.println("taskInvokeServerAddr请求地址:"+strUrl);
            URL url = new URL(strUrl);
            HttpURLConnection connection  = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String newLine=reader.readLine();
            String line = "";
            while(newLine!=null){
                line +=newLine;
                newLine += reader.readLine();
            }
            System.out.println("响应:"+line);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
