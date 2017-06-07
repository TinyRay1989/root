package com.git.yanlei.persistence.service;

import java.util.List;

import com.git.yanlei.persistence.entity.Staff;
import com.git.yanlei.persistence.entity.TeachingStaff;

public interface StaffService {
    void saveStaffs();

    List<TeachingStaff> findTeachingStaffBySname(String sname);

    List<Staff> findStaffBySnameStartingWith(String sname);
}
