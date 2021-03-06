package com.git.yanlei.jpa.entity.single_table;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value = "2")
public class NonTeachingStaff extends Staff {

    private static final long serialVersionUID = 4179496741991513104L;

    @Column(name = "C_AREA_EXPERTISE")
    private String areaExpertise;

    public NonTeachingStaff() {
        super();
    }

    public NonTeachingStaff(int sId, String sName, String areaExpertise) {
        super(sId, sName);
        this.areaExpertise = areaExpertise;
    }

    public String getAreaExpertise() {
        return areaExpertise;
    }

    public void setAreaExpertise(String areaExpertise) {
        this.areaExpertise = areaExpertise;
    }

    @Override
    public String toString() {
        return "NonTeachingStaff [areaExpertise=" + areaExpertise + ", getSid()=" + getSid()
                + ", getSname()=" + getSname() + "]";
    }

}
