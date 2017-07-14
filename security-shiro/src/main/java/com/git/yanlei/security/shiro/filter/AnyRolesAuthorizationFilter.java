package com.git.yanlei.security.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/**  
 * @Description: 只要有一个角色匹配就通过
 * @see org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
 * @author yanlei
 * @date 2017年7月5日 下午11:33:29
 * @version V1.0
 */ 
public class AnyRolesAuthorizationFilter extends AuthorizationFilter  {

    AnyRolesAuthorizationFilter(){
        setLoginUrl("/login");
        setUnauthorizedUrl("/unauthorized");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
            Object mappedValue) throws Exception {
        //没有设置角色，默认成功
        if(mappedValue == null){
            return true;
        }
        String[] roles = (String[])mappedValue;
        for(String role : roles){
            if(getSubject(request, response).hasRole(role)){
                return true;
            }
        }
        return false;
    }
}
