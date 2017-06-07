package com.git.yanlei.persistence.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.git.yanlei.persistence.entity.TeachingStaff;

@Repository(value = "teachingStaffRepository")
public interface TeachingStaffRepository extends JpaRepository<TeachingStaff, Serializable> {

    public List<TeachingStaff> findBySname(String sname);
}
