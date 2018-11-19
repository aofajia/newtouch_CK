package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.BWConfig;
import com.ruoyi.system.domain.StoreConfig;
import com.ruoyi.system.domain.Storemanger;
import com.ruoyi.system.mapper.BWConfigMapper;
import com.ruoyi.system.mapper.RechargeLogMapper;
import com.ruoyi.system.mapper.StoremangerMapper;
import com.ruoyi.system.service.IOrderCheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderCheckingServiceImpl implements IOrderCheckingService
{
    @Autowired
    private StoremangerMapper storemangerMapper;
    @Autowired
    private BWConfigMapper bwConfigMapper;

    @Override
    public List<Storemanger> selectStoreAll()
    {
        List<Storemanger> storemangers = storemangerMapper.selectAllStore();

        return storemangers;
    }

    @Override
    public List<StoreConfig> selectStoreConfigList()
    {
        List<StoreConfig> storeConfigs = bwConfigMapper.selectStoreConfigAll();
        return storeConfigs;
    }
}
