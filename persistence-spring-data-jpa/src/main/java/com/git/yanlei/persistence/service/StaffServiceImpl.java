package com.git.yanlei.persistence.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.git.yanlei.persistence.dao.NonTeachingStaffRepository;
import com.git.yanlei.persistence.dao.StaffRepository;
import com.git.yanlei.persistence.dao.TeachingStaffRepository;
import com.git.yanlei.persistence.entity.NonTeachingStaff;
import com.git.yanlei.persistence.entity.Staff;
import com.git.yanlei.persistence.entity.TeachingStaff;

@Service(value = "staffService")
public class StaffServiceImpl implements StaffService {

    @Resource
    private StaffRepository staffRepository;
    
    @Resource
    private TeachingStaffRepository teachingStaffRepository;
    
    @Resource
    private NonTeachingStaffRepository nonTeachingStaffRepository;

    @Transactional
    @Override
    public void saveStaffs() {
        Staff s = new Staff("Tom");
        // Teaching staff entity
        TeachingStaff ts1 = new TeachingStaff("Gopal", "MSc MEd", "Maths");
        TeachingStaff ts2 = new TeachingStaff("Manisha", "BSc BEd", "English");
        // Non-Teaching Staff entity
        NonTeachingStaff nts1 = new NonTeachingStaff("Satish", "Accounts");
        NonTeachingStaff nts2 = new NonTeachingStaff("Krishna", "Office Admin");

        staffRepository.save(s);
        staffRepository.save(ts1);
        staffRepository.save(ts2);
        staffRepository.save(nts1);
        staffRepository.save(nts2);
    }

    @Override
    public List<TeachingStaff> findTeachingStaffBySname(String sname) {
        return teachingStaffRepository.findBySname(sname);
    }

    @Override
    public List<Staff> findStaffBySnameStartingWith(String sname) {
        return staffRepository.findBySnameStartingWith(sname);
    }
    

}
