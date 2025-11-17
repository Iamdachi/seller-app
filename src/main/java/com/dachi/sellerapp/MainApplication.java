package com.dachi.sellerapp;

import com.dachi.sellerapp.config.*;

public class MainApplication {

    public static void main(String[] args) throws Exception {

        TomcatConfig tomcatConfig = new TomcatConfig();
        SpringContextFactory contextFactory = new SpringContextFactory();
        WebAppInitializer initializer = new WebAppInitializer();

        var tomcat = tomcatConfig.createTomcat();
        var springCtx = contextFactory.createWebContext();
        initializer.registerSpringDispatcher(tomcat, springCtx);

        tomcat.start();
        tomcat.getServer().await();
    }
}
