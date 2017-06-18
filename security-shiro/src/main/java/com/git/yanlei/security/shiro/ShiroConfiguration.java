package com.git.yanlei.security.shiro;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ShiroConfiguration {

    @Bean(name = "securityManager")
    public SecurityManager securityManager() {
        DefaultSecurityManager manager = new DefaultSecurityManager();
        // manager.setAuthenticator(authenticator());
        manager.setRealms(realms());
        return manager;
    }

    @Bean(name = "authenticator")
    public Authenticator authenticator() {
        ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
        modularRealmAuthenticator.setRealms(realms());
        return modularRealmAuthenticator;
    }

    @Bean(name = "realms")
    public Collection<Realm> realms() {
        Collection<Realm> realms = new ArrayList<Realm>();
        realms.add(initIniRealm());
        return realms;
    }

    private IniRealm initIniRealm() {
        IniRealm realm = new IniRealm();
        Ini ini = new Ini();
        ini.loadFromPath("classpath:shiro-user.ini");
        realm.setIni(ini);
        realm.init();
        return realm;
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodinvokingfactorybean = new MethodInvokingFactoryBean();
        methodinvokingfactorybean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodinvokingfactorybean.setArguments(new Object[] { securityManager() });
        return methodinvokingfactorybean;
    }
}
