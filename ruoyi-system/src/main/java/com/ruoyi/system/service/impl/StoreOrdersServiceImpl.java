package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.StoreOrders;
import com.ruoyi.system.mapper.StoreOrdersMapper;
import com.ruoyi.system.service.StoreOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class StoreOrdersServiceImpl implements StoreOrdersService {

    @Autowired
    StoreOrdersMapper storeOrdersMapper;

    public void insertStoreOrders(StoreOrders storeOrders){
        try{
            StoreOrders ifnull = storeOrdersMapper.selectByPrimaryKey(storeOrders.getOrderid());
            if(ifnull == null)
            {
                int i= storeOrdersMapper.insertSelective(storeOrders);
                System.out.println(i);
            }
            else
            {
                int i=storeOrdersMapper.updateByPrimaryKeySelective(storeOrders);
                System.out.println(i);
            }
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }

    }
}