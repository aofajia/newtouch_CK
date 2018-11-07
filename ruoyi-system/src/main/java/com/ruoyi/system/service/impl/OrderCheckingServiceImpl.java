package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.Storemanger;
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

    @Override
    public List<Storemanger> selectStoreAll()
    {
        List<Storemanger> storemangers = storemangerMapper.selectAllStore();

        return storemangers;
    }
}
