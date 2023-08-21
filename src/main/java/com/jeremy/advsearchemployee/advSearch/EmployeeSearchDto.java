package com.jeremy.advsearchemployee.advSearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSearchDto {
     private List<SearchCriteria> searchCriteriaList;
     private String dataOption;
}
