package com.git.yanlei.security.shiro.authenticator.strategy;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AbstractAuthenticationStrategy;
import org.apache.shiro.util.CollectionUtils;

/**  
 * @Description: 只允许一个 Realm 验证成功，并返回Realm身份验证成功的认证信息
 * @author yanlei
 * @date 2017年5月17日 上午3:04:03
 * @version V1.0
 */ 
public class OnlyOneSuccessfulStrategy  extends AbstractAuthenticationStrategy{

    @Override
    public AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException {
        if (aggregate == null 
                || CollectionUtils.isEmpty(aggregate.getPrincipals()) 
                || aggregate.getPrincipals().getRealmNames().size() != 1) {
            throw new AuthenticationException(
                    "Authentication token of type [" + token.getClass() + "] "
                    + "could not be authenticated by any configured realms.  "
                    + "Please ensure that only one realm can authenticate these tokens.");
        }
        return aggregate;
    }
}
