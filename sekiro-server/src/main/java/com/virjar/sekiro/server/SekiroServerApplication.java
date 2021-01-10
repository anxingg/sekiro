package com.virjar.sekiro.server;


import com.virjar.sekiro.server.taskinvoke.TaskInvoke;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SekiroServerApplication extends SpringBootServletInitializer {

    @Value("${name}")
    private String name;
    @Value("${taskInvokeServerAddr}")
    private String taskInvokeServerAddr;

    public static void main(String[] args) {
        SpringApplication.run(SekiroServerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SekiroServerApplication.class);
    }

    @PostConstruct
    public void init() {
        //加载注册
        Thread t = new Thread(new TaskInvoke(name,taskInvokeServerAddr));
        t.start();
    }

}
