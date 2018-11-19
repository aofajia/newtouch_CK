package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.RechargeLog;
import com.ruoyi.system.domain.SysRole;

import java.util.List;

public interface RechargeLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(RechargeLog record);

    int insertSelective(RechargeLog record);

    RechargeLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RechargeLog record);

    int updateByPrimaryKey(RechargeLog record);

    public List<RechargeLog> selectRoleList(RechargeLog record);
}