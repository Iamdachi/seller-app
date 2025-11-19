package com.dachi.sellerapp;

import com.dachi.sellerapp.config.WebAppInitializer;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class MainApplication {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(9090); // bind port
        // tomcat.setHostname("127.0.0.1"); // remove this line

        WebAppInitializer initializer = new WebAppInitializer();
        initializer.onStartup(tomcat);

        tomcat.start();

        System.out.println("Tomcat started on port " + tomcat.getConnector().getLocalPort());

        tomcat.getServer().await();
    }
}

