package com.jeremy.advsearchemployee.service;

import com.jeremy.advsearchemployee.dao.DepartmentRepository;
import com.jeremy.advsearchemployee.model.Department;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepository deptRepository;

    public List<Department> listAll(){
        return deptRepository.findAll();
    }

    public void save(Department department){
        deptRepository.save(department);
    }

    public List<Department> saveAll(List<Department> departments) { return deptRepository.saveAll(departments);}

    public Department get(Long deptId){
        return deptRepository.findById(deptId).isPresent() ? deptRepository.findById(deptId).get(): null;
    }

    public void delete(Long deptId){
        deptRepository.deleteById(deptId);
    }
}

