package com.git.yanlei.security.shiro.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ShiroExceptionHandler {
    
    @ModelAttribute(name = "test")
    public String setAttribute(){
        return "testAttribute";
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, true));  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
    
    @ExceptionHandler(value = {UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView processUnauthenticatedException(NativeWebRequest request, UnauthorizedException e){
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", e);
        mv.setViewName("unauthorized");
        return mv;  
    }
    
    @ExceptionHandler(value = {AuthorizationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView processAuthorizationException(NativeWebRequest request, AuthorizationException e){
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", e);
        mv.setViewName("login");
        return mv;
    }
}
