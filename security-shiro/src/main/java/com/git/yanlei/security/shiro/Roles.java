package com.git.yanlei.security.shiro;

public enum Roles {
    ADMIN("admin", "管理员"), USER("user", "用户"), GUEST("guest", "访客");

    private String name;
    private String desc;

    private Roles(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return getName();
    }
}
