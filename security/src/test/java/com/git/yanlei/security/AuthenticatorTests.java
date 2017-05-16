package com.git.yanlei.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticatorTests {

    private static final Logger log = LoggerFactory.getLogger(AuthenticatorTests.class);

    @Test
    public void contextLoads() {
    }

    @Test
    public void testFileConfig() {
        Subject subject = loginAs_ZhangSan("classpath:shiro-user.ini");
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testSingleRealm() {
        Subject subject = loginAs_ZhangSan("classpath:shiro-single-realm.ini");
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    /**
     * @Description: <pre>
     * multiRealm默认情况
     * securityManager.authenticator=ModularRealmAuthenticator
     * securityManager.authenticator.authenticationStrategy=AtLeastOneSuccessfulStrategy
     * </pre>
     * @author yanlei
     * @date 2017年5月13日 上午3:43:54
     * @version V1.0
     */
    @Test
    public void testMultiRealm() {
        Subject subject = loginAs_ZhangSan("classpath:shiro-multi-realm.ini");
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testJdbcRealm() {
        Subject subject = loginAs_ZhangSan("classpath:shiro-jdbc-realm.ini");
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testAllSuccessfulRealm() {
        Subject subject = loginAs_ZhangSan("classpath:shiro-all-successful-strategy.ini");
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testAtLeastOnSusscessfulRealm() {
        Subject subject = loginAs_ZhangSan("classpath:shiro-at-least-one-successful-strategy.ini");
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testFirstSuccessfulRealm() {
        Subject subject = loginAs_ZhangSan("classpath:shiro-first-successful-strategy.ini");
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testAtLeastTwoSuccessfulRealm() {
        Subject subject = loginAs_ZhangSan("classpath:shiro-at-least-two-successful-strategy.ini");
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testOnlyOneSuccessfulRealm() {
        Subject subject = loginAs_ZhangSan("classpath:shiro-only-one-successful-strategy.ini");
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    private Subject loginAs_ZhangSan(String iniResourcePath) {
        String username = "zhangsan";
        String password = "123";
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(iniResourcePath);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            log.info("{}登陆失败", username, e);
            return subject;
        }
        log.info("{}登陆成功", username);
        return subject;
    }
    
    @After
    public void cleanSubject(){
        ThreadContext.unbindSubject();
    }

}
