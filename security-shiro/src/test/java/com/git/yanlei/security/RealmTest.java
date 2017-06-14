package com.git.yanlei.security;

import javax.annotation.Resource;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.git.yanlei.security.shiro.dao.UserRepository;
import com.git.yanlei.security.shiro.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RealmTest {

    @Resource
    UserRepository userRepository;
    
    @Resource
    private CacheManager cacheManager;
    
    @Test
    public void test(){
        User entity = new User("zhangsan", "123");
        userRepository.save(entity );
    }
}
