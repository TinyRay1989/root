package com.git.yanlei.security.shiro.authenticator.strategy;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AbstractAuthenticationStrategy;
import org.apache.shiro.util.CollectionUtils;

public class AtLeastTwoSuccessfulStrategy extends AbstractAuthenticationStrategy{
    
    @Override
    public AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException {
        if (aggregate == null
                || CollectionUtils.isEmpty(aggregate.getPrincipals())
                || aggregate.getPrincipals().getRealmNames().size() < 2) {
            throw new AuthenticationException(
                    "Authentication token of type [" + token.getClass() + "] "
                    + "could not be authenticated by any configured realms.  "
                    + "Please ensure that at least two realm can authenticate these tokens.");
        }
        return aggregate;
    }
}
