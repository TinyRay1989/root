package com.git.yanlei.security.shiro;

import org.apache.shiro.session.mgt.SimpleSession;

public class OnlineSession extends SimpleSession {
    private static final long serialVersionUID = 2793775961197737028L;
    private String userAgent; // 用户浏览器类型
    private OnlineStatus status = OnlineStatus.on_line; // 在线状态
    private String systemHost; // 用户登录时系统IP

    public static enum OnlineStatus {
        on_line("在线"), hidden("隐身"), force_logout("强制退出");
        private final String info;

        private OnlineStatus(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public OnlineStatus getStatus() {
        return status;
    }

    public void setStatus(OnlineStatus status) {
        this.status = status;
    }

    public String getSystemHost() {
        return systemHost;
    }

    public void setSystemHost(String systemHost) {
        this.systemHost = systemHost;
    }

    @Override
    public String toString() {
        return "OnlineSession [userAgent=" + userAgent + ", status=" + status + ", systemHost="
                + systemHost + ", getHost()=" + getHost() + "]";
    }

}
