package com.git.yanlei.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.QueryHint;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.git.yanlei.persistence.dao.DepartmentRepository;
import com.git.yanlei.persistence.dao.EmployeeRepository;
import com.git.yanlei.persistence.entity.Department;
import com.git.yanlei.persistence.entity.Employee;

@Service(value = "employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeRepository employeeRepository;

    @Resource
    private DepartmentRepository departmentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.MANDATORY )
    @Override
    public void saveEmployees() {
        Department department = new Department("software");

        Employee employee1 = new Employee("Manisha", 40000.0, "接待员", department);
        Employee employee2 = new Employee("Masthanvali", 40000.0, "技术作家", department);
        Employee employee3 = new Employee("Satish", 30000.0, "技术作家", department);
        Employee employee4 = new Employee("Krishna", 30000.0, "技术作家", department);
        Employee employee5 = new Employee("Kiran", 35000.0, "接待员", department);

        departmentRepository.save(department);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);
        employeeRepository.save(employee4);
        employeeRepository.save(employee5);
    }

    @Override
    public Employee findByNameWithNameQuery(String employeeName) {
        Query query = entityManager.createNamedQuery("findEmployeeByName");
        query.setParameter("name", employeeName);
        query.setHint("org.hibernate.cacheable", true);
        return (Employee) query.getSingleResult();
    }

    @Override
    public Employee findByNameWithQuery(String employeeName) {
        List<Employee> employees1 = employeeRepository.findByEname(employeeName);
        //@SuppressWarnings("unused")
        //List<Employee> employees2 = employeeRepository.findByEname_native(employeeName);
        Employee employee = (Employee) CollectionUtils.get(employees1, 0);
        return employee;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.git.yanlei.persistence.service.EmployeeService#findByNameWithExample
     * (java.lang.String)
     */
    @Override
    public Employee findByNameWithExample(String employeeName) {
        Employee employee = new Employee();
        employee.setEname(employeeName);
        Example<Employee> example = Example.of(employee);
        List<Employee> employees = employeeRepository.findAll(example);
        return (Employee) CollectionUtils.get(employees, 0);
    }

    @Override
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
    public Employee findByNameWithJpaSpecificationExecutor(final String employeeName) {
        return employeeRepository.findOne(new Specification<Employee>() {

            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                /*return cb.equal(root.get("ename").as(String.class), employeeName);*/
                Predicate predicate = cb.equal(root.get("ename").as(String.class), employeeName);
                return query.where(predicate).getRestriction();
                /*
                or
                List<Predicate> predicateList = new ArrayList<Predicate>();
                predicateList.add(cb.equal(root.get("ename").as(String.class), employeeName));
                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).getRestriction();
                 */
            }
        });
    }

    @Override
    public List<Employee> findByConditionOrderBySalary(String startingWithEmployeeName,
            String departmentName) {
        return employeeRepository.findByEnameStartingWithAndDepartment_NameOrderBySalary(
                startingWithEmployeeName, departmentName);
    }

    @Override
    public List<Employee> findByConditionOrderBy(String startingWithEmployeeName,
            String departmentName, String orderByColumnName) {
        List<Order> orders = new ArrayList<Order>();
        Order order = new Order(Direction.ASC, orderByColumnName);
        orders.add(order);
        Sort sort = new Sort(orders);
        return employeeRepository.findByEnameStartingWithAndDepartment_Name(
                startingWithEmployeeName, departmentName, sort);
    }

    @Override
    public Page<Employee> findByConditionOrderBySalary(String startingWithEmployeeName,
            String departmentName, int page, int size) {

        Pageable pageable = new PageRequest(page, size, Direction.ASC, "salary");
        return employeeRepository.findByEnameStartingWithAndDepartment_Name(
                startingWithEmployeeName, departmentName, pageable);
    }
}
