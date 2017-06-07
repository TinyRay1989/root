package com.git.yanlei.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.git.yanlei.persistence.entity.Department;

@Repository(value = "departmentRepository")
public interface DepartmentRepository extends JpaRepository<Department, Integer>{

}
