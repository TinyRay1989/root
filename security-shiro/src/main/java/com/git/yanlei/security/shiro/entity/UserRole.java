package com.git.yanlei.security.shiro.entity;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class UserRole implements Serializable {
    private static final long serialVersionUID = -3394388345220029485L;
    private Long userId;
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserRole userRole = (UserRole) o;

        if (roleId != null ? !roleId.equals(userRole.roleId) : userRole.roleId != null)
            return false;
        if (userId != null ? !userId.equals(userRole.userId) : userRole.userId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserRole{" + "userId=" + userId + ", roleId=" + roleId + '}';
    }
}
