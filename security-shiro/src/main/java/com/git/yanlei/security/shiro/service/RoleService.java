package com.git.yanlei.security.shiro.service;

import com.git.yanlei.security.shiro.entity.Role;

public interface RoleService {
    public Role create(Role role);
    public void delete(Long roleId);
    public void correlationPermissions(Long roleId, Long ... permissionIds );
    public void unCorrelationPermissions(Long roleId, Long ... permissionIds );
}
