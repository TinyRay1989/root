package com.git.yanlei.security.shiro.realm.credentialsmatcher;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    /*private Ehcache passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher() {
        CacheManager cacheManager = CacheManager.newInstance(CacheManager.class.getClassLoader()
                .getResource("ehcache.xml"));
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }*/
    
    private Cache<String, AtomicInteger> passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        /*String username = token.getPrincipal().toString();
        Element element = passwordRetryCache.get(username);
        if(element == null){
            element = new Element(username, new AtomicInteger(0));
            passwordRetryCache.put(element);
        }
        AtomicInteger atomicInteger = (AtomicInteger) element.getObjectValue();
        if(atomicInteger.incrementAndGet() > 5){
            throw new ExcessiveAttemptsException();
        }
        boolean result = super.doCredentialsMatch(token, info);
        if(result){
            passwordRetryCache.remove(username);
        }
        return result;*/
        
        String username = (String)token.getPrincipal();
        //retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        if(retryCount.incrementAndGet() > 5) {
            //if retry count > 5 throw
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            //clear retry count
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}
