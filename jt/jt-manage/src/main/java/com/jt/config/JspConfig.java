package com.jt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.File;

@Configuration
@PropertySource("classpath:/properties/manege_jsp.properties")
public class JspConfig {
    @Value("${manage.jsp.webapp}")
    private String manageJspAddr;
    @Bean
    public AbstractServletWebServerFactory embeddedServletContainerFactory(){
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        tomcatServletWebServerFactory.setDocumentRoot(new File(manageJspAddr));

        return tomcatServletWebServerFactory;
    }
}
