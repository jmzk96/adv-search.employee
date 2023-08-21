package com.jeremy.advsearchemployee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DEPARTMENT")
public class Department {

    @Id
    @Column(name = "DEPT_ID")
    private Long deptId;

    @Column(name = "DEPT_NAME")
    private String deptName;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<Employee> emps;


}
