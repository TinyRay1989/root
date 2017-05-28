package com.git.yanlei.security.shiro.service;

import java.util.Set;

import com.git.yanlei.security.shiro.entity.User;

public interface UserService {
    public User create(User user);
    public void delete(Long userId);
    public void correlationRoles(Long userId, Long...roleIds);
    public void unCorrelationRoles(Long userId, Long...roleIds);
    public User find(String username);
    public Set<String> findRoles(String username);
    public Set<String> findPermissions(String username);
}
