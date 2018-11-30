package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Employee;

import java.util.List;

public interface EmployeeMapper {
    int insert(Employee record);

    int insertSelective(Employee record);

    List<Employee> openCardDataList();
}