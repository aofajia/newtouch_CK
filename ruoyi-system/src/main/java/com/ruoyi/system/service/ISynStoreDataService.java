package com.ruoyi.system.service;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 订单对账业务层
 * 
 * @author
 */
@Repository
public interface ISynStoreDataService
{
    /**
     * 统计会员表sdb_b2c_members 余额advance  总和
     *
     * @return
     */
    public int sumMemberadvance(Logger syn_storedata_logger) throws Exception;
    /**
     * 自动订单对账功能
     *
     * @return
     */
    public void orderChecking(Logger syn_storedata_logger) throws Exception;
    /**
     * 预存款供应商余额记录功能
     *
     * @return
     */
    public void getStoreAdvance(Logger syn_storedata_logger) throws Exception;
    /**
     * 获取供应商订单功能
     *
     * @return
     */
    public void getStoreOrders(Logger syn_storedata_logger) throws Exception;

}
