package com.jeremy.advsearchemployee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeremy.advsearchemployee.model.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EMPLOYEE")
public class Employee {
    @Id
    @Column(name = "EMPLOYEE_ID")
    private Long empId;

    @Column(name = "EMP_LASTNM")
    private String empLastNm;

    @Column(name ="EMP_FIRSTNM")
    private String empFirstNm;

    @Column(name ="JOB_NM")
    private String jobNm;

    @Column(name = "MGR_ID", nullable = true)
    private Long managerId;

    @Column(name = "HIREDT")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate hireDt;

    @Column(name = "SALARY")
    private double salary;

    @Column(name ="COMMISION",nullable = true)
    private double commision;

    @ManyToOne
    @JoinColumn(name= "DEPT_ID")
    private Department department;

}
