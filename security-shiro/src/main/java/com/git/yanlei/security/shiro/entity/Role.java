package com.git.yanlei.security.shiro.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "sys_roles")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "userCache")
@Cacheable(true)
public class Role implements Serializable {
    private static final long serialVersionUID = 7037793812835469613L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String role; // 角色标识 程序中判断使用,如"admin"
    private String description; // 角色描述,UI界面显示使用
    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
        name = "sys_roles_permissions",
        joinColumns = {@JoinColumn(
            name = "role_id",
            foreignKey = @ForeignKey(
                foreignKeyDefinition = "none",
                value = ConstraintMode.NO_CONSTRAINT
                )
            )
        }, 
        inverseJoinColumns = {@JoinColumn(
            name = "permission_id",
            foreignKey = @ForeignKey(
                foreignKeyDefinition = "none",
                value = ConstraintMode.NO_CONSTRAINT
                )
            )
        }
    )
    private Set<Permission> permissions = new HashSet<Permission>();

    public Role() {
    }

    public Role(String role, String description, Boolean available) {
        this.role = role;
        this.description = description;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Role role = (Role) o;

        if (id != null ? !id.equals(role.id) : role.id != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Role{" + "id=" + id + ", role='" + role + '\'' + ", description='" + description
                + '\'' + ", available=" + available + '}';
    }
}
