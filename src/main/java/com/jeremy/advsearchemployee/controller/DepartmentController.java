package com.jeremy.advsearchemployee.controller;


import com.jeremy.advsearchemployee.exceptions.RecordNotFoundException;
import com.jeremy.advsearchemployee.model.Department;
import com.jeremy.advsearchemployee.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.NoSuchElementException;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RepositoryEntityLinks entityLinks;
    // RESTful API methods for Retrieval operations
    @GetMapping("/departments")
    public List<Department> list(){
        return departmentService.listAll();
    }

    @GetMapping("/departments/{deptId}")
    public ResponseEntity<Department> get(@PathVariable Long deptId){
        try {
            Department department = departmentService.get(deptId);
            if (department == null){
                throw new RecordNotFoundException("Department Id does not exist");
            }
            return new ResponseEntity<Department>(department, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
        }
    }


    // RESTful API method for Create operation

    @PostMapping("/departments")
    public void add(@RequestBody Department department){
        departmentService.save(department);
    }


    @PostMapping("/departments/bulk")
    public ResponseEntity<?> addBulk(@RequestBody List<Department> departments){
        System.out.println(departments.toString());
        Department singleDepartment = null;
        List<Department> departmentList = new ArrayList<>();
        try{
            for (Department department: departments){
                System.out.println("department dept_id: "+ department.getDeptId().toString());
                Department departmentById = departmentService.get(department.getDeptId());
                System.out.println(departmentById);
                if (departmentById == null){
                    departmentService.save(department);
                    departmentList.add(department);
                }else {
                    System.out.println("Duplicate id :" + department.getDeptId().toString());
                    throw new RecordNotFoundException("Duplicate(s) exist(s) ");
                }
            }

//            departmentList = departmentService.saveAll(departments);
        } catch (Exception e){
            e.printStackTrace();
            throw new RecordNotFoundException("Something went wrong ");
        }
         return new ResponseEntity<>(departmentList,HttpStatus.OK);
    }


    // RESTful API method for Update operation
    @PostMapping("departments/{deptId}")
    public ResponseEntity<?> update(@RequestBody Department department, @PathVariable Long deptId){
        try{
           Department existDepartment = departmentService.get(deptId);
           existDepartment.setDeptId(department.getDeptId());
           existDepartment.setDeptName(department.getDeptName());
           departmentService.save(existDepartment);
           return new ResponseEntity<Department>(existDepartment, HttpStatus.OK);

        } catch (NoSuchElementException e){
            return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
        }
    }

    // RESTful API method for Delete operation

    @DeleteMapping("/departments/{deptId}")
    public void delete(@PathVariable Long deptId){
        departmentService.delete(deptId);
    }

}
