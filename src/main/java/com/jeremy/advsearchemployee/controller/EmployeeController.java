package com.jeremy.advsearchemployee.controller;

import com.jeremy.advsearchemployee.advSearch.EmployeeSearchDto;
import com.jeremy.advsearchemployee.advSearch.EmployeeSpecBuilder;
import com.jeremy.advsearchemployee.advSearch.SearchCriteria;
import com.jeremy.advsearchemployee.exceptions.RecordNotFoundException;
import com.jeremy.advsearchemployee.model.Employee;
import com.jeremy.advsearchemployee.service.EmployeeService;
import com.jeremy.advsearchemployee.util.APIresponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import  org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

import java.util.NoSuchElementException;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // RESTful API methods for Retrieval operations
    @GetMapping("/employees")
    public List<Employee> list(){
        return employeeService.listAll();
    }

    @GetMapping("/employees/{empId}")
    public ResponseEntity<Employee> get(@PathVariable Long empId){
        try {
            Employee employee = employeeService.get(empId);
            return new ResponseEntity<Employee>(employee, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
    }


    // RESTful API method for Create operation

    @PostMapping("/employees")
    public void add(@RequestBody Employee employee){
        employeeService.save(employee);
    }

    // RESTful API method for Update operation

    @PostMapping("/employees/bulk")
    public ResponseEntity<?> addBUlk(@RequestBody List<Employee> employees){
        System.out.println(employees.toString());
        Employee singleEmployee = null;
        List<Employee> employeeList = new ArrayList<>();
        try{
            for (Employee employee: employees){
                System.out.println("Employee emp_id: "+ employee.getEmpId().toString());
                Employee employeeById = employeeService.get(employee.getEmpId());
                System.out.println(employeeById);
                if (employeeById == null){
                    employeeService.save(employee);
                    employeeList.add(employee);
                }else {
                    System.out.println("Duplicate id :" + employee.getEmpId().toString());
                    throw new RecordNotFoundException("Duplicate(s) exist(s)");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RecordNotFoundException("Not all entities saved");
        }
        return new ResponseEntity<>(employeeList,HttpStatus.OK);
    }

    @PostMapping("employees/{empId}")
    public ResponseEntity<?> update(@RequestBody Employee employee, @PathVariable Long empId){
        try{
            Employee existEmployee = employeeService.get(empId);
            existEmployee.setEmpId(employee.getEmpId());
            existEmployee.setEmpLastNm(employee.getEmpLastNm());
            existEmployee.setEmpFirstNm(employee.getEmpFirstNm());
            existEmployee.setJobNm(employee.getJobNm());
            existEmployee.setManagerId(employee.getManagerId());
            existEmployee.setHireDt(employee.getHireDt());
            existEmployee.setSalary(employee.getSalary());
            existEmployee.setCommision(employee.getCommision());
            existEmployee.setDepartment(employee.getDepartment());
            employeeService.save(existEmployee);
            return new ResponseEntity<Employee>(existEmployee, HttpStatus.OK);

        } catch (NoSuchElementException e){
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
    }



    // RESTful API method for Delete operation

    @DeleteMapping("/employees/{empId}")
    public void delete(@PathVariable Long empId){
        employeeService.delete(empId);
    }


    // RESTful API method for Search operation

    @PostMapping("/employees/search")
    public ResponseEntity<?> searchEmployees(@RequestParam(name = "pageNum",defaultValue = "0") int pageNum,
                                             @RequestParam(name = "pageSize",defaultValue = "3") int pageSize,
                                             @RequestBody EmployeeSearchDto employeeSearchDto){
        System.out.println("employeeSearchDto:" + employeeSearchDto.toString());
        APIresponse apIresponse = new APIresponse();

        EmployeeSpecBuilder builder = new EmployeeSpecBuilder();
        List<SearchCriteria> criteriaList = employeeSearchDto.getSearchCriteriaList();
        if(criteriaList != null){
            criteriaList.forEach(x->{x.setDataOption(employeeSearchDto.getDataOption());
            builder.with(x);});

        }
        Pageable page = PageRequest.of(pageNum, pageSize, Sort.by("empFirstNm")
                .ascending().and(Sort.by("empLastNm"))
                .ascending().and(Sort.by("department")).ascending());

        Page<Employee> employeePage = employeeService.findBySearchCriteria(builder.build(),  page);
        System.out.println("Builder: "+builder.toString());
        System.out.println("Builder params: "+builder.params.toString());
        apIresponse.setData(employeePage.toList());
        apIresponse.setMessage("Successfully retrieved employee Recored");
        apIresponse.setReponseCode(HttpStatus.OK);
        return new ResponseEntity<>(apIresponse,apIresponse.getReponseCode());

    }
}
