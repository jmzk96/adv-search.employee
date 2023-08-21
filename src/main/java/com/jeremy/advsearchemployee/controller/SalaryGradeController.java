package com.jeremy.advsearchemployee.controller;

import com.jeremy.advsearchemployee.exceptions.RecordNotFoundException;
import com.jeremy.advsearchemployee.model.Department;
import com.jeremy.advsearchemployee.model.SalaryGrade;
import com.jeremy.advsearchemployee.service.SalaryGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class SalaryGradeController {

    @Autowired
    private SalaryGradeService salaryGradeService;

    @GetMapping("/salary_grade")
    public List<SalaryGrade> list(){ return salaryGradeService.listAll();}

    @GetMapping("/salary_grade/{grade}")
    public ResponseEntity<SalaryGrade> get(@PathVariable Long grade){
        try {
            SalaryGrade salaryGrade = salaryGradeService.get(grade);
            return new ResponseEntity<SalaryGrade>(salaryGrade, HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<SalaryGrade>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/salary_grade")
    public void add(@RequestBody SalaryGrade salaryGrade) {salaryGradeService.save(salaryGrade);}

    @PostMapping("/salary_grade/bulk")
    public ResponseEntity<?> addBulk(@RequestBody List<SalaryGrade> salaryGrades){
        System.out.println(salaryGrades.toString());
        Department singleSalaryGrade = null;
        List<SalaryGrade> salaryGradeList = new ArrayList<>();
        try{
            for (SalaryGrade salaryGrade: salaryGrades){
                System.out.println("salary grade : "+ salaryGrade.getGrade().toString());
                SalaryGrade salaryGradeByGrade = salaryGradeService.get(salaryGrade.getGrade());
                System.out.println(salaryGradeByGrade);
                if (salaryGradeByGrade == null){
                    salaryGradeService.save(salaryGrade);
                    salaryGradeList.add(salaryGrade);
                }else {
                    System.out.println("Duplicate id :" + salaryGrade.getGrade().toString());
                    throw new RecordNotFoundException("Duplicate(s) exist(s) ");
                }
            }

//            departmentList = departmentService.saveAll(departments);
        } catch (Exception e){
            e.printStackTrace();
            throw new RecordNotFoundException("Something went wrong ");
        }
        return new ResponseEntity<>(salaryGradeList,HttpStatus.OK);
    }


    @PostMapping("/salary_grade/{grade}")
    public ResponseEntity<?> update(@RequestBody SalaryGrade salaryGrade, @PathVariable Long grade){
        try{
            SalaryGrade existSalaryGrade = salaryGradeService.get(grade);
            existSalaryGrade.setGrade(salaryGrade.getGrade());
            existSalaryGrade.setMinSalary(salaryGrade.getMinSalary());
            existSalaryGrade.setMaxSalary(salaryGrade.getMaxSalary());
            salaryGradeService.save(existSalaryGrade);
            return new ResponseEntity<SalaryGrade>(existSalaryGrade, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<SalaryGrade>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/salary_grade/{grade}")
    public void delete(@PathVariable Long grade){salaryGradeService.delete(grade);}
}
