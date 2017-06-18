package com.git.yanlei.security.shiro.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.git.yanlei.security.shiro.entity.Permission;

@Repository(value = "permissionRepository")
public interface PermissionRepository extends JpaRepository<Permission, Long>{

}
