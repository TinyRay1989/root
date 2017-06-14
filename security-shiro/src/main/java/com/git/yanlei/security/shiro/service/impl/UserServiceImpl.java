package com.git.yanlei.security.shiro.service.impl;

import java.util.Set;

import com.git.yanlei.security.shiro.entity.User;
import com.git.yanlei.security.shiro.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User create(User user) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(Long userId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void correlationRoles(Long userId, Long... roleIds) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void unCorrelationRoles(Long userId, Long... roleIds) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public User find(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> findRoles(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> findPermissions(String username) {
        // TODO Auto-generated method stub
        return null;
    }
}
