package com.git.yanlei.security;

import java.util.Arrays;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.git.yanlei.security.util.LoginUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleTests {

    @Test
    public void testHasRole() {
        Subject subject = LoginUtils.loginAs_ZhangSan("classpath:shiro-role.ini");
        Assert.assertTrue(subject.hasRole("role1"));
        Assert.assertTrue(subject.hasAllRoles(Arrays.asList("role1", "role2")));
        boolean[] result = subject.hasRoles(Arrays.asList("role1", "role2", "role3"));
        Assert.assertTrue(result[0]);
        Assert.assertTrue(result[1]);
        Assert.assertFalse(result[2]);
    }

    @Test
    public void testCheckRole() {
        Subject subject = LoginUtils.loginAs_ZhangSan("classpath:shiro-role.ini");
        subject.checkRole("role1");
        subject.checkRoles(Arrays.asList("role1", "role2"));
    }

    @Test
    public void testIsisPermitted() {
        Subject subject = LoginUtils.loginAs_ZhangSan("classpath:shiro-permission.ini");
        Assert.assertTrue(subject.isPermitted("user:create"));
        Assert.assertFalse(subject.isPermitted("user:view"));
        Assert.assertTrue(subject.isPermittedAll("user:create", "user:update", "user:delete"));
        boolean[] result = subject.isPermitted("user:create", "user:update", "user:delete");
        Assert.assertTrue(result[0]);
        Assert.assertTrue(result[1]);
        Assert.assertTrue(result[2]);
    }
    
    @Test
    public void testCheckPermission1() {
        Subject subject = LoginUtils.loginAs_ZhangSan("classpath:shiro-permission.ini");
        subject.checkPermission("user:create");
        subject.checkPermissions("user:create", "user:update", "user:delete");
        //subject.checkPermission("user:view");
    }
    
    @Test
    public void testCheckPermission2() {
        Subject subject = LoginUtils.loginAs_LiSi("classpath:shiro-permission.ini");
        subject.checkPermission("user:view:1");
    }
    
    @Test
    public void testWildcardPermission() {
        Subject subject = LoginUtils.loginAs_LiSi("classpath:shiro-permission.ini");
        subject.checkPermission("user:view:1");
        subject.checkPermission(new WildcardPermission("user:view:1"));
    }
    
    @Test
    public void testPermission() {
        Subject subject = LoginUtils.loginAs_ZhangSan("classpath:shiro-authorazer.ini");
        subject.checkPermission("user1:view:1");
        subject.checkPermission("user2:view:1");
        subject.checkPermission("user1:*");
        subject.checkPermission("+user1+2");
        subject.checkPermission("+user1+8");
        subject.checkPermission("+user2+10");
        //subject.checkPermission("+user1+4");
        subject.checkPermission("menu:view");
    }

    @After
    public void cleanSubject() {
        ThreadContext.unbindSubject();
    }

}
