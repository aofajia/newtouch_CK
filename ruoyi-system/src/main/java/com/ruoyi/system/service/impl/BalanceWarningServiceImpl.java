package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.BWConfigtype;
import com.ruoyi.system.mapper.BWConfigtypeMapper;
import com.ruoyi.system.service.IBalanceWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class BalanceWarningServiceImpl implements IBalanceWarningService
{
    @Autowired
    private BWConfigtypeMapper bwConfigtypeMapper;

    @Override
    public List<BWConfigtype> selectConfigTypeAll()
    {
        List<BWConfigtype> bwConfigtypes = bwConfigtypeMapper.selectConfigTypeAll();
        return bwConfigtypes;
    }

    @Override
    public String checkStoreName(String configname)
    {
        Map map = bwConfigtypeMapper.checkStoreName(configname);
        String count = map.get("COUNT(0)").toString();
        if("0".equals(count))
        {
            //供应商不存在 报错
        }
        return "0";
    }
}
