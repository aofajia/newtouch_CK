package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Company;

import java.util.List;

public interface CompanyMapper {
    int deleteByPrimaryKey(Long companyId);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Long companyId);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);

    List<Company> selectAllCompany();
}