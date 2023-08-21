package com.jeremy.advsearchemployee.service;


import com.jeremy.advsearchemployee.dao.SalaryGradeRepository;

import com.jeremy.advsearchemployee.model.SalaryGrade;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SalaryGradeService {

    @Autowired
    private SalaryGradeRepository salRepository;

    public List<SalaryGrade> listAll(){
        return salRepository.findAll();
    }

    public void save(SalaryGrade salaryGrade){
        salRepository.save(salaryGrade);
    }

    public List<SalaryGrade> saveAll(List<SalaryGrade> salaryGrades) {return salRepository.saveAll(salaryGrades);}

    public SalaryGrade get(Long salId){
        return salRepository.findById(salId).isPresent() ? salRepository.findById(salId).get() : null;
    }

    public void delete(Long salId){
        salRepository.deleteById(salId);
    }
}
