package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.BalanceRecord;
import com.ruoyi.system.domain.Members;
import com.ruoyi.system.domain.MembersWithBLOBs;

public interface MembersMapper {
    int deleteByPrimaryKey(Integer memberId);

    int insert(MembersWithBLOBs record);

    int insertSelective(MembersWithBLOBs record);

    MembersWithBLOBs selectByPrimaryKey(Integer memberId);

    int updateByPrimaryKeySelective(MembersWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(MembersWithBLOBs record);

    int updateByPrimaryKey(Members record);

    BalanceRecord sumMemberadvance();
}