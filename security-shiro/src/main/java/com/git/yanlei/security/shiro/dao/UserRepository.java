package com.git.yanlei.security.shiro.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.git.yanlei.security.shiro.entity.User;

@Repository(value = "userRepository")
public interface UserRepository extends JpaRepository<User, Long>{
    User findById(@Param(value = "id")Long id);

    User findByUsername(String username);

    @Modifying
    @Query(value = "update User u set u.password = :password where u.id = :id")
    void changePassword(@Param(value = "id") Long id, @Param(value = "password") String password);
}
