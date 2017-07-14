package com.git.yanlei.security.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpMethod;

/**  
 * @Description: onPreHandle 主要流程：
 * <pre>
 * 首先判断是否已经登录过了，如果已经登录过了继续拦截器链即可；
 * 如果没有登录，看看是否是登录请求，如果是 get 方法的登录页面请求，则继续拦截器链（到请求页面），否则如果是 get 方法的其他页面请求则保存当前请求并重定向到登录页面；
 * 如果是 post 方法的登录页面表单提交请求，则收集用户名 / 密码登录即可，如果失败了保存错误消息到 “shiroLoginFailure” 并返回到登录页面；
 * 如果登录成功了，且之前有保存的请求，则重定向到之前的这个请求，否则到默认的成功页面。
 * </pre>
 * @author yanlei
 * @date 2017年7月5日 下午6:07:46
 * @version V1.0
 */ 
public class FormLoginFilter extends PathMatchingFilter{
    
    private String loginUrl = "/login";
    
    private String successUrl = "/";

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response,
            Object mappedValue) throws Exception {
        if(SecurityUtils.getSubject().isAuthenticated()){
            return true;
        }
        
        boolean isLoginUrl = pathsMatch(loginUrl, request);
        if(isLoginUrl){
            HttpServletRequest req = (HttpServletRequest) request;
            if(HttpMethod.POST.name().equalsIgnoreCase(req.getMethod())){
                boolean loginSuccess = login(request);
                if(loginSuccess){
                    return redirectToSuccessUrl(request, response);
                }
            }
            return true;
        }else{
            saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }

    private boolean redirectToSuccessUrl(ServletRequest request, ServletResponse response)
            throws IOException {
        WebUtils.redirectToSavedRequest(request, response, successUrl);
        return false;
    }
    
    private void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException{
        WebUtils.saveRequest(request);
        WebUtils.issueRedirect(request, response, loginUrl);
    }

    private boolean login(ServletRequest request) {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
        } catch (Exception e) {
            request.setAttribute("shiroLoginFailure", e.getClass());
            return false;
        }
        return true;
    }
}
