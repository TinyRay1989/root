package com.git.yanlei.security;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.git.yanlei.security.util.LoginUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticatorTests {

    public static final Logger log = LoggerFactory.getLogger(AuthenticatorTests.class);

    @Test
    public void contextLoads() {
    }

    @Test
    public void testFileConfig() {
        Subject subject = LoginUtils.loginAs_ZhangSan("classpath:shiro-user.ini");
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testSingleRealm() {
        Subject subject = LoginUtils.loginAs_ZhangSan("classpath:shiro-single-realm.ini");
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
        Subject subject = LoginUtils.loginAs_ZhangSan("classpath:shiro-multi-realm.ini");
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testJdbcRealm() {
        Subject subject = LoginUtils.loginAs_ZhangSan("classpath:shiro-jdbc-realm.ini");
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testAllSuccessfulRealm() {
        Subject subject = LoginUtils.loginAs_ZhangSan("classpath:shiro-all-successful-strategy.ini");
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testAtLeastOnSusscessfulRealm() {
        Subject subject = LoginUtils.loginAs_ZhangSan("classpath:shiro-at-least-one-successful-strategy.ini");
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testFirstSuccessfulRealm() {
        Subject subject = LoginUtils.loginAs_ZhangSan("classpath:shiro-first-successful-strategy.ini");
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testAtLeastTwoSuccessfulRealm() {
        Subject subject = LoginUtils.loginAs_ZhangSan("classpath:shiro-at-least-two-successful-strategy.ini");
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testOnlyOneSuccessfulRealm() {
        Subject subject = LoginUtils.loginAs_ZhangSan("classpath:shiro-only-one-successful-strategy.ini");
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    @After
    public void cleanSubject(){
        ThreadContext.unbindSubject();
    }

}
