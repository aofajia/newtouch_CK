package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.HRFI_Employee;

import java.util.List;

public interface HRFI_EmployeeMapper {
    int deleteByPrimaryKey(String id);

    int insert(HRFI_Employee record);

    int insertSelective(HRFI_Employee record);

    HRFI_Employee selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HRFI_Employee record);

    int updateByPrimaryKey(HRFI_Employee record);

    List<HRFI_Employee> list();
}