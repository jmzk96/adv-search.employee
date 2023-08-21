package com.jeremy.advsearchemployee.advSearch;

import com.jeremy.advsearchemployee.model.Employee;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecBuilder {
    public final List<SearchCriteria> params;

    public EmployeeSpecBuilder(){
        this.params = new ArrayList<>();
    }

    public final EmployeeSpecBuilder with(String key, String operation, Object value){
        params.add(new SearchCriteria(key,operation,value));
        return this;

    }

    public final EmployeeSpecBuilder with(SearchCriteria searchCriteria){
        params.add(searchCriteria);
        return this;
    }

    public Specification<Employee> build(){
        if (params.size()==0){
            return null;
        }

        Specification<Employee> result = new EmployeeSpecification(params.get(0));

        for (int i=1; i < params.size(); i++){

            SearchCriteria searchCriteria = params.get(i);
            result = SearchOperation.getDataOption(searchCriteria.getDataOption())==SearchOperation.ALL
                    ? Specification.where(result).and(new EmployeeSpecification(searchCriteria))
                    : Specification.where(result).or(new EmployeeSpecification(searchCriteria));


        }
        return result;
    }
}
