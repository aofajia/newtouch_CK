package com.ruoyi.system.service;

import com.ruoyi.system.domain.BWConfig;
import com.ruoyi.system.domain.StoreConfig;
import com.ruoyi.system.domain.Storemanger;
import com.ruoyi.system.mapper.RechargeLogMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
    /**
     * 查询商场金额流动详情
     *
     * @return 员工消费总金额、员工剩余账户总金额、离职提现额度、商城充值总金额MAP

     */
    public Map chinkingMemberAdvance(String startTime);
    /**
     * 查询供应商
     *
     * @return 供应商对账数据
     */
    public List storeChecking(String startTime);
    /**
     * 查询差异订单
     *
     * @return 差异订单列表
     */
    public List gatDifferenceOrderList(String id,String startTime);

}
