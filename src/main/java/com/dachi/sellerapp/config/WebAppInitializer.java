package com.dachi.sellerapp.config;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.io.File;

/**
 * Manually bootstraps a Spring MVC application inside an embedded Tomcat instance.
 * Creates the Tomcat context, initializes the Spring application context,
 * registers the DispatcherServlet, and maps it to the root path.
 */
public class WebAppInitializer {

    /**
     * Initializes Spring MVC within the provided embedded Tomcat server.
     * Creates the application context, loads configuration, and registers
     * the DispatcherServlet on "/".
     */
    public void onStartup(Tomcat tomcat) throws Exception {

        Context webContext = tomcat.addContext("", new File(".").getAbsolutePath());

        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.scan("com.dachi.sellerapp");   // scan everything under sellerapp
        ctx.register(AppConfig.class);  // <-- important!
        ctx.setServletContext(webContext.getServletContext());
        ctx.refresh();

        // Optional: log beans to ensure controllers are detected
        //System.out.println("Beans registered in Spring context:");
        for (String bean : ctx.getBeanDefinitionNames()) {
            //System.out.println(bean);
        }

        // core of spring MVC
        DispatcherServlet dispatcher = new DispatcherServlet(ctx);
        Tomcat.addServlet(webContext, "dispatcher", dispatcher).setLoadOnStartup(1);
        webContext.addServletMappingDecoded("/", "dispatcher");
    }


}
