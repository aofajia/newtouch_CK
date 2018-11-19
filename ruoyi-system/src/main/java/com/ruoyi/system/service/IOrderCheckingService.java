package com.ruoyi.system.service;

import com.ruoyi.system.domain.BWConfig;
import com.ruoyi.system.domain.StoreConfig;
import com.ruoyi.system.domain.Storemanger;
import com.ruoyi.system.mapper.RechargeLogMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单对账业务层
 * 
 * @author
 */
@Repository
public interface IOrderCheckingService
{
    /**
     * 查询所有供应商信息
     *
     * @return 供应商信息列表
     */
    public List<Storemanger> selectStoreAll();
    /**
     * 查询所有配置供应商信息
     *
     * @return 供应商信息列表
     */
    public List<StoreConfig> selectStoreConfigList();
}
