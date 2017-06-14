package com.git.yanlei.persistence.entity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @Description: <pre>
 * <li>事务型（transactional）：隔离级别最高，对于经常被读但很少被改的数据，可以采用此策略。
 * 因为它可以防止脏读与不可重复读的并发问题。发生异常的时候，缓存也能够回滚（系统开销大）。
 * <li>读写型（read-write）：对于经常被读但很少被改的数据，可以采用此策略。因为它可以防止脏读的并发问题。
 * 更新缓存的时候会锁定缓存中的数据。
 * <li>非严格读写型（nonstrict-read-write）：不保证缓存与数据库中数据的一致性。
 * 对于极少被改，并且允许偶尔脏读的数据，可采用此策略。不锁定缓存中的数据。
 * <li>只读型（read-only）：对于从来不会被修改的数据，可使用此策略。
 * </pre>
 * @author yanlei
 * @date 2017年6月9日 下午9:45:09
 * @version V1.0
 */
@Entity
@Table(name = "T_EMPLOYEE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
@Cacheable(true)
@NamedQuery(name = "findEmployeeByName", query = "select e from Employee e where e.ename = :name ")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eid;
    private String ename;
    private Double salary;
    private String deg;

    @OneToOne
    private Department department;

    public Employee(String ename, Double salary, String deg) {
        super();
        this.ename = ename;
        this.salary = salary;
        this.deg = deg;
    }

    public Employee(Integer eid, String ename, Double salary, String deg) {
        super();
        this.eid = eid;
        this.ename = ename;
        this.salary = salary;
        this.deg = deg;
    }

    public Employee(String ename, Double salary, String deg, Department department) {
        super();
        this.ename = ename;
        this.salary = salary;
        this.deg = deg;
        this.department = department;
    }

    public Employee(Integer eid, String ename, Double salary, String deg, Department department) {
        super();
        this.eid = eid;
        this.ename = ename;
        this.salary = salary;
        this.deg = deg;
        this.department = department;
    }

    public Employee() {
        super();
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }

    @Override
    public String toString() {
        return "Employee [eid=" + eid + ", ename=" + ename + ", salary=" + salary + ", deg=" + deg
                + ", department=" + department + "]";
    }

}