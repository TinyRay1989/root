package com.git.yanlei.persistence.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.git.yanlei.persistence.entity.NonTeachingStaff;

@Repository(value = "nonTeachingStaffRepository")
public interface NonTeachingStaffRepository extends JpaRepository<NonTeachingStaff, Serializable>{
    
    public List<NonTeachingStaff> findTeachingStaffBySname(String sname);
    

}
