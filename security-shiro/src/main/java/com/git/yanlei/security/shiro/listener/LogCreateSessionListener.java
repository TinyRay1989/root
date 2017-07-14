package com.git.yanlei.security.shiro.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

public class LogCreateSessionListener extends SessionListenerAdapter{
    @Override
    public void onStart(Session session) {
        System.out.println("会话创建：" + session.getId());
    }
}
