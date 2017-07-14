package com.git.yanlei.security.shiro.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AnnotationController {
    
    @RequiresAuthentication
    @RequestMapping(value = "hello1")
    public String hello1(HttpServletRequest request){
        SecurityUtils.getSubject().checkRoles("admin");
        return "success";
    }
    
    @RequiresAuthentication
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.AND)
    @RequestMapping(value = "hello2")
    public String hello2(){
        return "success";
    }
}
