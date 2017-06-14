package com.git.yanlei.security.shiro.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.git.yanlei.security.shiro.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>{

}
