package com.git.yanlei.security.autoconfigure.tomcat;

import java.nio.charset.Charset;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.setUriEncoding(Charset.forName("UTF-8"));
        tomcat.addAdditionalTomcatConnectors(createSslConnector());
        return tomcat;
    }

    private Connector createSslConnector() {
        Connector connector = new Connector();
        connector.setScheme("https");
        connector.setSecure(true);
        connector.setPort(443);
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        protocol.setSSLEnabled(true);
        protocol.setSslProtocol("TLS");
        protocol.setKeystoreFile("D:/localhost.keystore");
        protocol.setKeystorePass("123456");
        //protocol.setKeyAlias("192.168.1.4");
        return connector;
    }
}