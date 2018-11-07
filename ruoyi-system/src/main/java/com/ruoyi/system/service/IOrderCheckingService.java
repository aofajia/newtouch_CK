package com.ruoyi.system.service;

import com.ruoyi.system.domain.Storemanger;
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
}
