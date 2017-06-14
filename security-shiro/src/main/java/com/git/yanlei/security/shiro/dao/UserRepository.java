package com.git.yanlei.security.shiro.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.git.yanlei.security.shiro.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    User findById(@Param(value = "id")Long id);
}
