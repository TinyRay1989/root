package com.git.yanlei.security.shiro.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.git.yanlei.security.shiro.entity.Role;

@Repository(value = "roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long>{

}
