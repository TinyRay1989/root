package com.git.yanlei.security.shiro.service;

import com.git.yanlei.security.shiro.entity.Permission;

public interface PermissionService {
    public Permission create(Permission permission);
    public void delete(Long permissionId);
}
