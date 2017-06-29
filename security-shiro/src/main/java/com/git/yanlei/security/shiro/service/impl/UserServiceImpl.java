package com.git.yanlei.security.shiro.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.git.yanlei.security.shiro.PasswordHelper;
import com.git.yanlei.security.shiro.dao.PermissionRepository;
import com.git.yanlei.security.shiro.dao.RoleRepository;
import com.git.yanlei.security.shiro.dao.UserRepository;
import com.git.yanlei.security.shiro.entity.Permission;
import com.git.yanlei.security.shiro.entity.Role;
import com.git.yanlei.security.shiro.entity.User;
import com.git.yanlei.security.shiro.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;
    
    @Resource
    private RoleRepository roleRepository;
    
    @Resource
    private PermissionRepository permissionRepository;

    @Override
    public User create(User user) {
        PasswordHelper.encryptPassword(user);
        return userRepository.save(user);
    }

    @Override
    public void delete(Long userId) {
        userRepository.delete(userId);
    }

    @Override
    @Transactional
    public void correlationRoles(Long userId, Long... roleIds) {
        User user = userRepository.findOne(userId);
        List<Role> roleList = roleRepository.findAll(Arrays.asList(roleIds));
        for(Role role : roleList){
            user.getRoles().add(role);
        }
        userRepository.save(user);
    }

    @Override
    public void unCorrelationRoles(Long userId, Long... roleIds) {
        User user = userRepository.findOne(userId);
        Set<Role> userSet = user.getRoles();
        Iterator<Role> iterator = userSet.iterator();
        List<Long> idList = Arrays.asList(roleIds);
        while(iterator.hasNext()){
            if(idList.contains(iterator.next().getId())){ 
                iterator.remove();
            }
        }
        userRepository.save(user);
    }

    @Override
    public User find(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Set<String> findRoles(String username) {
        User user = userRepository.findByUsername(username);
        Set<String> result = new HashSet<String>();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            result.add(role.getRole());
        }
        return result;
    }

    @Override
    public Set<String> findPermissions(String username) {
        User user = userRepository.findByUsername(username);
        Set<String> result = new HashSet<String>();
        for (Role role : user.getRoles()) {
            for (Permission permission : role.getPermissions()) {
                result.add(permission.getPermission());
            }
        }
        return result;
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findOne(userId);
        user.setPassword(newPassword);
        PasswordHelper.encryptPassword(user);
        userRepository.changePassword(user.getId(), user.getPassword());
        
    }
}
