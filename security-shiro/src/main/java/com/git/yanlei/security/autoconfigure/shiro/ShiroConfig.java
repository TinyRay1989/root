package com.git.yanlei.security.autoconfigure.shiro;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.git.yanlei.security.shiro.realm.UserRealm;
import com.git.yanlei.security.shiro.realm.credentialsmatcher.RetryLimitHashedCredentialsMatcher;

@Configuration
public class ShiroConfig {

    @Bean(name = "shiroCacheManager")
    public CacheManager shiroCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return em;
    }

    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher(@Qualifier("shiroCacheManager") CacheManager cacheManager) {
        RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(
                shiroCacheManager());
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    @Bean(name = "userRealm")
    public Realm userRealm(@Qualifier("credentialsMatcher") CredentialsMatcher credentialsMatcher) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher);
        userRealm.setCachingEnabled(true);
        userRealm.setAuthenticationCachingEnabled(true);
        userRealm.setAuthenticationCacheName("authenticationCache");
        userRealm.setAuthorizationCachingEnabled(true);
        userRealm.setAuthorizationCacheName("authorizationCache");
        return userRealm;
    }

    @Bean(name = "securityManager")
    public SecurityManager securityManager(
            @Qualifier("userRealm") Realm userRealm,
            @Qualifier("shiroCacheManager") CacheManager cacheManager
            ) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setCacheManager(shiroCacheManager());
        return securityManager;
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(
            @Qualifier("securityManager") SecurityManager securityManager) {
        MethodInvokingFactoryBean methodinvokingfactorybean = new MethodInvokingFactoryBean();
        methodinvokingfactorybean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodinvokingfactorybean.setArguments(new Object[] { securityManager });
        return methodinvokingfactorybean;
    }
    
    
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistrationBean.addInitParameter("targetFilterLifecycle", "true");
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
    
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("securityManager") SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager  
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/user");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        setFilter(shiroFilterFactoryBean);
        setFilterChainDefinitionMap(shiroFilterFactoryBean);
        return shiroFilterFactoryBean;
    }

    /**  
     * @Description: 
     * <pre>
     * anon(AnonymousFilter.class),
     * authc(FormAuthenticationFilter.class),
     * authcBasic(BasicHttpAuthenticationFilter.class),
     * logout(LogoutFilter.class),
     * noSessionCreation(NoSessionCreationFilter.class),
     * perms(PermissionsAuthorizationFilter.class),
     * port(PortFilter.class),
     * rest(HttpMethodPermissionFilter.class),
     * roles(RolesAuthorizationFilter.class),
     * ssl(SslFilter.class),
     * user(UserFilter.class);
     * </pre>
     * @author yanlei
     * @date 2017年6月28日 下午1:48:33
     * @version V1.0
     * @param shiroFilterFactoryBean
     */ 
    private void setFilter(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        Map<String, Filter> filters = new HashMap<String, Filter>();
        RolesAuthorizationFilter rolesAuthorizationFilter = new RolesAuthorizationFilter();
        rolesAuthorizationFilter.setUnauthorizedUrl("/unauthorized");
        filters.put("roles", rolesAuthorizationFilter);
        shiroFilterFactoryBean.setFilters(filters );
    }

    /**  
     * @Description:  
     * <pre>
     * anon：它对应的过滤器里面是空的,什么都没做
     * authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
     * roles[admin]拦截器表示需要有 admin 角色授权才能访问
     * perms["user:create"]拦截器表示需要有 “user:create” 权限才能访问。
     * </pre>
     * @author yanlei
     * @date 2017年6月21日 上午1:26:18
     * @version V1.0
     * @param shiroFilterFactoryBean
     */ 
    private void setFilterChainDefinitionMap(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/unauthorized", "anon");
        
        filterChainDefinitionMap.put("/user", "authc");// 这里为了测试，只限制/user，实际开发中请修改为具体拦截的请求规则
        filterChainDefinitionMap.put("/user/edit/**", "authc,perms[user:edit]");
        filterChainDefinitionMap.put("/role", "authc,roles[admin]");
        //filterChainDefinitionMap.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
    
    /**  
     * @Description: 添加 Shiro Spring AOP 权限注解的支持（需要开启spring AOP）
     * @author yanlei
     * @date 2017年6月18日 下午5:32:31
     * @version V1.0
     * @param securityManager
     * @return
     */ 
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
