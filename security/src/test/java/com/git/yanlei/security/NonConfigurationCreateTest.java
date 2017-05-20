package com.git.yanlei.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.druid.pool.DruidDataSource;
import com.git.yanlei.security.shiro.authorizer.permission.BitAndWildPermissionResolver;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NonConfigurationCreateTest {

    @Test
    public void nonConfigurationCreateTest() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        securityManager.setAuthenticator(authenticator);
        
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setPermissionResolver(new BitAndWildPermissionResolver());
        securityManager.setAuthorizer(authorizer);
        
        JdbcRealm realm = new JdbcRealm();
        DruidDataSource ds = new DruidDataSource ();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://192.168.1.5:3306/shiro");
        ds.setUsername("root");
        ds.setPassword("root");
        realm.setDataSource(ds);
        realm.setPermissionsLookupEnabled(true);
        securityManager.setRealm(realm);
        
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken("zhangsan", "123");
        subject.login(token);
        subject.checkRole("role1");
        subject.checkPermission("user1:create");
    }
    
    @After
    public void cleanSubject(){
        ThreadContext.unbindSubject();
    }
}
