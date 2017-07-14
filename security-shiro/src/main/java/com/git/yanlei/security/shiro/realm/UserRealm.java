package com.git.yanlei.security.shiro.realm;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.git.yanlei.security.shiro.entity.User;
import com.git.yanlei.security.shiro.service.UserService;

public class UserRealm extends AuthorizingRealm {

    private static final String NAME = "userRealm";

    @Resource
    private UserService userService;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
        String username = (String) principals.getPrimaryPrincipal();
        authInfo.setRoles(userService.findRoles(username));
        authInfo.setStringPermissions(userService.findPermissions(username));
        return authInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        String username = (String) token.getPrincipal();
        User user = userService.find(username);
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (user.getLocked()) {
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(user.getUsername(),
                user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
        return authInfo;
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

}
