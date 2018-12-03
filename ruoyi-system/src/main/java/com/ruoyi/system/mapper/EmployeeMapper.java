package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Employee;
import com.ruoyi.system.domain.EmployeeExample;

import java.util.List;

public interface EmployeeMapper {
    int insert(Employee record);

    int insertSelective(Employee record);

    List<EmployeeExample> openCardDataList();

    List<EmployeeExample> openCardData(List<String> list);

    int updateByEmpoloyeeId(String id);

    List<EmployeeExample> openCardDataListById(String id);
}