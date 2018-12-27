package com.ruoyi.system.service;

import org.slf4j.Logger;

import java.util.Date;

public interface JdSynStoreDataService {
    public void queryOrder(Logger syn_storedata_logger, Date startTime, String productType, Integer pageNo, String jdOrderIdIndex, int eachIndex);

    public void queryBalance(Logger syn_storedata_logger, String store_id, Date startTime, String payType, int eachIndex);
}
