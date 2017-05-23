package com.git.yanlei.security.shiro.realm;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.realm.AuthenticatingRealm;

public class EncryptRealm extends AuthenticatingRealm {

    private PasswordService passwordService;

    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = String.valueOf((char[]) token.getCredentials());
        String encryptedPassword = passwordService.encryptPassword(password);

        if (!StringUtils.equals("zhangsan", username)) {
            throw new UnknownAccountException();
        }
        if (!StringUtils.equals("123", password)) {
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(username, encryptedPassword, getName());
    }
}