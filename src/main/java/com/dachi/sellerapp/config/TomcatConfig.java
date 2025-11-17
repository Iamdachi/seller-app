package com.dachi.sellerapp.config;

import org.apache.catalina.startup.Tomcat;

/**
 * Configure Tomcat Web Server, since not using boot.
 * We can later: add HTTPS, change ports, add compression. All without touching Spring or business logic.
 */
public class TomcatConfig {

    public static Tomcat createTomcat() {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();  // required
        return tomcat;
    }
}
