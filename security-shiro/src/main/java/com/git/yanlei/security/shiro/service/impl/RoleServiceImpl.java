package com.git.yanlei.security.shiro.service.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;



import org.springframework.stereotype.Service;

import com.git.yanlei.security.shiro.dao.PermissionRepository;
import com.git.yanlei.security.shiro.dao.RoleRepository;
import com.git.yanlei.security.shiro.entity.Permission;
import com.git.yanlei.security.shiro.entity.Role;
import com.git.yanlei.security.shiro.service.RoleService;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {
    
    @Resource
    private RoleRepository roleRepository;
    
    @Resource
    private PermissionRepository permisionRepository;

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void delete(Long roleId) {
        roleRepository.delete(roleId);
    }

    @Override
    @Transactional
    public void correlationPermissions(Long roleId, Long... permissionIds) {
        Role role = roleRepository.findOne(roleId);
        List<Permission> permissonList = permisionRepository.findAll(Arrays.asList(permissionIds));
        for(Permission permission : permissonList){
            role.getPermissions().add(permission);
        }
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public void unCorrelationPermissions(Long roleId, Long... permissionIds) {
        Role role = roleRepository.findOne(roleId);
        Set<Permission> permissons = role.getPermissions();
        Iterator<Permission> iterator = permissons.iterator();
        List<Long> idList = Arrays.asList(permissionIds);
        while(iterator.hasNext()){
            if(idList.contains(iterator.next().getId())){ 
                iterator.remove();
            }
        }
        roleRepository.save(role);
    }
}
