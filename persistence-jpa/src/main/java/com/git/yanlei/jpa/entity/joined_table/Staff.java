package com.git.yanlei.jpa.entity.joined_table;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity(name = "StaffJoin")
@Table(name = "T_STAFF_JOIN")
@Inheritance(strategy = InheritanceType.JOINED)
public class Staff implements Serializable {
    private static final long serialVersionUID = 6133945717170109155L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C_ID")
    private int sid;

    @Column(name = "C_NAME")
    private String sname;

    public Staff() {
        super();
    }

    public Staff(int sid, String sname) {
        super();
        this.sid = sid;
        this.sname = sname;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Override
    public String toString() {
        return "Staff [sid=" + sid + ", sname=" + sname + "]";
    }
}
