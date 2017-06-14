package com.git.yanlei.jpa.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "T_DEPARTMENT")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToMany(cascade = CascadeType.PERSIST)
    //@JoinColumn(name = "department_id")
    @JoinTable(
        name = "T_DEPARTMENT_EMPLOYEE", 
        joinColumns = {
            @JoinColumn(
                name = "department_id", 
                nullable = false,
                foreignKey = @ForeignKey(
                    name = "fk_departmentandemployee_department_on_department_id",
                    foreignKeyDefinition = "none",
                    value = ConstraintMode.NO_CONSTRAINT
                )
            )
        }, 
        inverseJoinColumns= {
            @JoinColumn(
                name = "employee_id", 
                nullable = false,
                foreignKey = @ForeignKey(
                    name = "fk_departmentandemployee_employee_on_employee_id",
                    foreignKeyDefinition = "none",
                    value = ConstraintMode.NO_CONSTRAINT
                )
            )
        }
    )
    //@Transient
    private Set<Employee> employeeSet = new HashSet<Employee>();;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String deptName) {
        this.name = deptName;
    }

    public Set<Employee> getEmployeeSet() {
        return employeeSet;
    }

    public void setEmployeeSet(Set<Employee> employeeSet) {
        this.employeeSet = employeeSet;
    }

    @Override
    public String toString() {
        return "Department [id=" + id + ", name=" + name + ", employeeSet=" + employeeSet + "]";
    }

    
}