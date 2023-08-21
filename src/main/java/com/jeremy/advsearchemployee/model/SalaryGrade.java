package com.jeremy.advsearchemployee.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SALARY_GRADE")
public class SalaryGrade {

    @Id
    @Column(name = "GRADE")
    private Long grade;

    @Column(name = "MIN_SALARY")
    private double minSalary;

    @Column(name = "MAX_SALARY")
    private double maxSalary;
}
