package com.git.yanlei.security;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.git.yanlei.security.shiro.ShiroConfiguration;
import com.git.yanlei.security.util.LoginUtils;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ShiroConfiguration.class)
@SpringBootTest
@Ignore
public class JavaConfigTests {

    @Test
    public void testFileConfig() {
        Subject subject = LoginUtils.loginAs_ZhangSan();
        Assert.assertTrue("is not authenticated", subject.isAuthenticated());
        subject.logout();
    }

    @After
    public void cleanSubject(){
        ThreadContext.unbindSubject();
    }

}
