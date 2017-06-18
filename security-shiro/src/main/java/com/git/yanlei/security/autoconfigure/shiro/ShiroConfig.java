package com.git.yanlei.security.autoconfigure.shiro;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.git.yanlei.security.shiro.realm.UserRealm;
import com.git.yanlei.security.shiro.realm.credentialsmatcher.RetryLimitHashedCredentialsMatcher;

@Configuration
public class ShiroConfig {
    
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodinvokingfactorybean = new MethodInvokingFactoryBean();
        methodinvokingfactorybean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodinvokingfactorybean.setArguments(new Object[] { securityManager() });
        return methodinvokingfactorybean;
    }
    
    @Bean(name = "shiroCacheManager")
    public CacheManager shiroCacheManager() {  
        EhCacheManager em = new EhCacheManager();  
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");  
        return em;  
    }

    @Bean(name = "securityManager")
    public DefaultSecurityManager securityManager() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(userRealm());
        securityManager.setCacheManager(shiroCacheManager());
        return securityManager;
    }

    @Bean(name = "userRealm")
    public Realm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher());
        userRealm.setCachingEnabled(true);
        userRealm.setAuthenticationCachingEnabled(true);
        userRealm.setAuthenticationCacheName("authenticationCache");
        userRealm.setAuthorizationCachingEnabled(true);
        userRealm.setAuthorizationCacheName("authorizationCache");
        return userRealm;
    }

    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(
                shiroCacheManager());
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }
    
   /* @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }*/

}
