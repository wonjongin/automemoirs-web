package io.github.wonjongin.automemoirsweb;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContainerConfig {
//    @Value("${ajp.port}")
//    int ajpPort;
//
//    @Bean
//    public ServletWebServerFactory servletContainer(){
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//        tomcat.addAdditionalTomcatConnectors(createAjpConnector());
//        return tomcat;
//
//    }
//    private Connector createAjpConnector() {
//        Connector ajpConnector = new Connector("AJP/1.3");
//        ajpConnector.setPort(ajpPort); ajpConnector.setSecure(false);
//        ajpConnector.setAllowTrace(false); ajpConnector.setScheme("http");
//        return ajpConnector;
//    }

}
