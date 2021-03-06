package com.git.yanlei.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity(name = "TeachingStaff")
@Table(name = "T_TEACHING_STAFF")
@PrimaryKeyJoinColumn(name =  "C_ID")
public class TeachingStaff extends Staff {

    private static final long serialVersionUID = -7845725089145034704L;

    @Column(name = "C_QUALIFICATION")
    private String qualification;
    @Column(name = "C_SUBJECT_EXPERTISE")
    private String subjectExpertise;

    public TeachingStaff() {
        super();
    }

    public TeachingStaff(String sName, String qualification, String subjectExpertise) {
        super(sName);
        this.qualification = qualification;
        this.subjectExpertise = subjectExpertise;
    }
    
    public TeachingStaff(int sId, String sName, String qualification, String subjectExpertise) {
        super(sId, sName);
        this.qualification = qualification;
        this.subjectExpertise = subjectExpertise;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSubjectExpertise() {
        return subjectExpertise;
    }

    public void setSubjectExpertise(String subjectExpertise) {
        this.subjectExpertise = subjectExpertise;
    }

    @Override
    public String toString() {
        return "TeachingStaff [qualification=" + qualification + ", subjectExpertise="
                + subjectExpertise + ", getSid()=" + getSid() + ", getSname()=" + getSname() + "]";
    }

}
