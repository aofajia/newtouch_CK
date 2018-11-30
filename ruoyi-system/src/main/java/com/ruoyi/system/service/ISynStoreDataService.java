package com.ruoyi.system.service;

import com.ruoyi.system.domain.BalanceRecord;
import com.ruoyi.system.domain.StoreConfig;
import com.ruoyi.system.domain.Storemanger;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
    public int sumMemberadvance(Logger syn_storedata_logger);
    /**
     * 自动订单对账功能
     *
     * @return
     */
    public void orderChecking(Logger syn_storedata_logger);

}
