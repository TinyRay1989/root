package com.git.yanlei.persistence.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.git.yanlei.persistence.entity.Employee;

public interface EmployeeService {
    void saveEmployees();

    List<Employee> findByConditionOrderBySalary(String startingWithEmployeeName,
            String departmentName);

    List<Employee> findByConditionOrderBy(String startingWithEmployeeName, String departmentName,
            String orderByColumnName);

    Page<Employee> findByConditionOrderBySalary(String startingWithEmployeeName,
            String departmentName, int page, int size);

    Employee findByNameWithNameQuery(String employeeName);

    Employee findByNameWithQuery(String employeeName);

    /**  
     * @Description: 使用example方式查询，需要实体类的属性全是基本类或者如下
     * 
     * 
     * <pre>public class PersonSpec implements Specification<Person> {

          private final Person example;
        
          public PersonSpec(Person example) {
            this.example = example;
          }
        
          @Override
          public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
            List<Predicate> predicates = new ArrayList<>();
        
            if (StringUtils.isNotBlank(example.getLastName())) {
              predicates.add(cb.like(cb.lower(root.get(Person_.lastName)), example.getLastName().toLowerCase() + "%"));
            }
        
            if (StringUtils.isNotBlank(example.getFirstName())) {
              predicates.add(cb.like(cb.lower(root.get(Person_.firstName)), example.getFirstName().toLowerCase() + "%"));
            }
        
            if (example.getEmployed() != null) {
              predicates.add(cb.equal(root.get(Person_.employed), example.getEmployed()));
            }
        
            if (example.getDob() != null) {
              predicates.add(cb.equal(root.get(Person_.dob), example.getDob()));
            }
        
            return andTogether(predicates, cb);
          }
        
          private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
            return cb.and(predicates.toArray(new Predicate[0]));
          }
        }</pre>
        
        <pre>
        Person example = new Person();
        example.setLastName("James");
        example.setEmployed(true);
        PersonSpec personSpec = new PersonSpec(example);
        List<Person> persons = personRepository.findAll(personSpec);
        </pre>
     * @author yanlei
     * @date 2017年6月8日 下午12:38:52
     * @version V1.0
     * @param employeeName
     * @return
     */ 
    Employee findByNameWithExample(String employeeName);

    Employee findByNameWithJpaSpecificationExecutor(String employeeName);

}
