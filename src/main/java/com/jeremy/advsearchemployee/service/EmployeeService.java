package com.jeremy.advsearchemployee.service;


import com.jeremy.advsearchemployee.dao.EmployeeRepository;


import com.jeremy.advsearchemployee.model.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepository empRepository;

    public List<Employee> listAll(){
        return empRepository.findAll();
    }

    public void save(Employee employee){
        empRepository.save(employee);
    }

    public List<Employee> saveAll(List<Employee> employees) { return empRepository.saveAll(employees);}

    public Employee get(Long empId){
        return empRepository.findById(empId).isPresent() ? empRepository.findById(empId).get(): null;
    }

    public Page<Employee> findBySearchCriteria(Specification<Employee> spec, Pageable page){
        Page<Employee> searchResult = empRepository.findAll(spec,page);
        return searchResult;
    }
    public void delete(Long empId){
        empRepository.deleteById(empId);
    }
}
