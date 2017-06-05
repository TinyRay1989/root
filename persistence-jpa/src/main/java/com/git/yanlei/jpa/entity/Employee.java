package com.git.yanlei.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "T_EMPLOYEE")
@NamedQuery(name = "findEmployeeByName", query = "select e from Employee e where e.ename = :name ")
public class Employee {
    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO) need hibernate_sequence
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eid;
    private String ename;
    private double salary;
    private String deg;
    //@OneToOne
    @Transient
    private Department department;

    public Employee(String ename, double salary, String deg) {
        super();
        this.ename = ename;
        this.salary = salary;
        this.deg = deg;
    }

    public Employee(int eid, String ename, double salary, String deg) {
        super();
        this.eid = eid;
        this.ename = ename;
        this.salary = salary;
        this.deg = deg;
    }

    public Employee() {
        super();
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee [eid=" + eid + ", ename=" + ename + ", salary=" + salary + ", deg=" + deg
                + ", department=" + department + "]";
    }

}