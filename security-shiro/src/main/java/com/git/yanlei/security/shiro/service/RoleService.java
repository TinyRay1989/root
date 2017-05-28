package com.git.yanlei.security.shiro.service;

import com.git.yanlei.security.shiro.entity.Role;

public interface RoleService {
    public Role create(Role role);
    public void delete(Long roleId);
    public void correlationPermission(Long roleId, Long ... permissionIds );
    public void unCorrelationPermission(Long roleId, Long ... permissionIds );
}
