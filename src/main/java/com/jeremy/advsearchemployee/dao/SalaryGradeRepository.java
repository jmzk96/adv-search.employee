package com.jeremy.advsearchemployee.dao;

import com.jeremy.advsearchemployee.model.SalaryGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryGradeRepository  extends JpaRepository<SalaryGrade,Long> {
}
