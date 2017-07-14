package com.git.yanlei.security.shiro.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.git.yanlei.security.shiro.service.UserService;

@Controller
public class LoginController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest req, HttpServletResponse rep) {
        String error = null;
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(false);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            error = "用户名/密码错误";
        } catch (IncorrectCredentialsException e) {
            error = "用户名/密码错误";
        } catch (AuthenticationException e) {
            // 其他错误，比如锁定，如果想单独处理请单独catch处理
            error = "其他错误：" + e.getMessage();
        }

        if (error != null) {// 出错了，返回登录页面
            req.setAttribute("error", error);
            return "login";
        } else {// 登录成功
            return "loginSuccess";
        }
    }

    @RequestMapping(value = "/formfilterlogin", method = RequestMethod.POST)
    public String formfilterlogin(HttpServletRequest req, HttpServletResponse rep) {
        String errorClassName = (String) req.getAttribute("shiroLoginFailure");

        if (UnknownAccountException.class.getName().equals(errorClassName)) {
            req.setAttribute("error", "用户名/密码错误");
        } else if (IncorrectCredentialsException.class.getName().equals(errorClassName)) {
            req.setAttribute("error", "用户名/密码错误");
        } else if (errorClassName != null) {
            req.setAttribute("error", "未知错误：" + errorClassName);
        }

        return "formfilterlogin";
    }

    @RequestMapping(value = "/authenticated", method = RequestMethod.GET)
    public String authenticated() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "authenticated";
        } else {
            return "login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "logoutSuccess";
    }

    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public String permission() {
        Subject subject = SecurityUtils.getSubject();
        subject.checkPermission("user:create");
        return "hasPermission";
    }

    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public String role() {
        Subject subject = SecurityUtils.getSubject();
        subject.checkRole("admin");
        return "hasRole";
    }

    @RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
    public String unauthorized() {
        return "unauthorized";
    }
    
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String page403() {
        return "redirect:/unauthorized";
    }
}
