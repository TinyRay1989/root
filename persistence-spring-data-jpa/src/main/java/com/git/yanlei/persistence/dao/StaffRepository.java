package com.git.yanlei.persistence.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.git.yanlei.persistence.entity.Staff;

@Repository(value = "staffRepository")
public interface StaffRepository extends JpaRepository<Staff, Serializable> {

    public List<Staff> findBySnameStartingWith(String sname);
}
