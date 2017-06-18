package com.git.yanlei.security.shiro.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.git.yanlei.security.shiro.dao.PermissionRepository;
import com.git.yanlei.security.shiro.entity.Permission;
import com.git.yanlei.security.shiro.service.PermissionService;

@Service(value = "permissionService")
public class PermissionServiceImpl implements  PermissionService{
    
    @Resource
    private PermissionRepository permisionRepository;

    @Override
    public Permission create(Permission permission) {
        return permisionRepository.save(permission);
    }

    @Override
    public void delete(Long permissionId) {
        permisionRepository.delete(permissionId);
    }
}
