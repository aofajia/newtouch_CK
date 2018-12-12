package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Employee;
import com.ruoyi.system.domain.EmployeeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int insert(Employee record);

    int insertSelective(Employee record);

    List<EmployeeExample> openCardDataList();

    List<EmployeeExample> openCardData();

    int updateByEmpoloyeeId(@Param("id") String id,@Param("status")Integer status);

    List<EmployeeExample> openCardDataListById(String id);
}