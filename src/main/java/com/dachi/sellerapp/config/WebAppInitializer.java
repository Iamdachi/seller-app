package com.dachi.sellerapp.config;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.io.File;

/**
 * Connects Spring to Tomcat.
 */
public class WebAppInitializer {

    public void registerSpringDispatcher(Tomcat tomcat,
                                         AnnotationConfigWebApplicationContext ctx) throws Exception {

        Context root = tomcat.addContext("", new File(".").getAbsolutePath());

        DispatcherServlet servlet = new DispatcherServlet(ctx);
        Tomcat.addServlet(root, "dispatcher", servlet).setLoadOnStartup(1);

        root.addServletMappingDecoded("/", "dispatcher");
    }
}
