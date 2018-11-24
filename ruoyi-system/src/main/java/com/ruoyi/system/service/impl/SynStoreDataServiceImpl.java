package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.IBalanceWarningService;
import com.ruoyi.system.service.ISynStoreDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class SynStoreDataServiceImpl implements ISynStoreDataService
{
    @Autowired
    private MembersMapper membersMapper;
    @Autowired
    private BalanceRecordMapper balanceRecordMapper;

    @Override
    public int sumMemberadvance()
    {
        BalanceRecord balanceRecord = membersMapper.sumMemberadvance();
        balanceRecord.setId(UUID.randomUUID().toString().toUpperCase());
        balanceRecord.setSupplierid("000");
        balanceRecord.setSupplierid("Mall");
        balanceRecord.setSuppliername("商城");
        Date date = new Date();
        balanceRecord.setCommitdate(date);
        int insert = balanceRecordMapper.insert(balanceRecord);
        return insert;
    }
}
