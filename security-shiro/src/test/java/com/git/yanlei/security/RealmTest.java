package com.git.yanlei.security;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.git.yanlei.security.shiro.entity.Permission;
import com.git.yanlei.security.shiro.entity.Role;
import com.git.yanlei.security.shiro.entity.User;
import com.git.yanlei.security.shiro.realm.UserRealm;
import com.git.yanlei.security.shiro.service.PermissionService;
import com.git.yanlei.security.shiro.service.RoleService;
import com.git.yanlei.security.shiro.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
//@Commit
@Rollback
public class RealmTest {
    
    @Resource
    protected PermissionService permissionService;
    @Resource
    protected RoleService roleService;
    @Resource
    protected UserService userService;

    @Resource
    private UserRealm userRealm;
    
    protected String password = "123";

    protected Permission p1;
    protected Permission p2;
    protected Permission p3;
    protected Role r1;
    protected Role r2;
    protected User u1;
    protected User u2;
    protected User u3;
    protected User u4;
    
    @Before
    public void setUp() {

        //1、新增权限
        p1 = new Permission("user:create", "用户模块新增", Boolean.TRUE);
        p2 = new Permission("user:update", "用户模块修改", Boolean.TRUE);
        p3 = new Permission("menu:create", "菜单模块新增", Boolean.TRUE);
        permissionService.create(p1);
        permissionService.create(p2);
        permissionService.create(p3);
        //2、新增角色
        r1 = new Role("admin", "管理员", Boolean.TRUE);
        r2 = new Role("user", "用户管理员", Boolean.TRUE);
        roleService.create(r1);
        roleService.create(r2);
        //3、关联角色-权限
        roleService.correlationPermissions(r1.getId(), p1.getId(), p2.getId(), p3.getId());
        roleService.correlationPermissions(r2.getId(), p1.getId(), p2.getId());

        //4、新增用户
        u1 = new User("zhang", password);
        u2 = new User("li", password);
        u3 = new User("wu", password);
        u4 = new User("wang", password);
        u4.setLocked(Boolean.TRUE);
        userService.create(u1);
        userService.create(u2);
        userService.create(u3);
        userService.create(u4);
        //5、关联用户-角色
        userService.correlationRoles(u1.getId(), r1.getId());

    }
    
    @Test
    public void login(){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(u1.getUsername(), password);
        subject.login(token);
        Assert.assertTrue(subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkPermission("user:create");
    }
    
    @Test
    public void changePassword(){
        Subject subject = SecurityUtils.getSubject();
        userService.changePassword(u1.getId(), password + "1");
        userRealm.clearCache(subject.getPrincipals());

        UsernamePasswordToken token = new UsernamePasswordToken(u1.getUsername(), password + "1");
        subject.login(token);
        subject.getSession(false);
        subject.getSession().getId();
        
        System.out.println(subject.getPreviousPrincipals());
        System.out.println(subject.getPrincipals().getRealmNames());
    }
}
