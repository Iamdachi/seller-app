package com.dachi.sellerapp.config;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.io.File;

public class WebAppInitializer {

    public void onStartup(Tomcat tomcat) throws Exception {

        Context webContext = tomcat.addContext("", new File(".").getAbsolutePath());

        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.scan("com.dachi.sellerapp");   // scan everything under sellerapp
        ctx.setServletContext(webContext.getServletContext());
        ctx.refresh();

        // Optional: log beans to ensure controllers are detected
        System.out.println("Beans registered in Spring context:");
        for (String bean : ctx.getBeanDefinitionNames()) {
            System.out.println(bean);
        }

        DispatcherServlet dispatcher = new DispatcherServlet(ctx);
        Tomcat.addServlet(webContext, "dispatcher", dispatcher).setLoadOnStartup(1);
        webContext.addServletMappingDecoded("/", "dispatcher");
    }
}
