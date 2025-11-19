package com.dachi.sellerapp.config;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * creates the Spring WebApplicationContext.
 */
public class SpringContextFactory {

    public AnnotationConfigWebApplicationContext createWebContext() {
        var ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(WebAppInitializer.class);
        return ctx;
    }
}
