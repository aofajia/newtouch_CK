package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.BalanceWarningConfig;
import com.ruoyi.system.domain.SdbBusinessStoremanger;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈余额预警设置〉
 *
 * @author aofajia
 * @create 2018/12/3
 * @since 1.0.0
 */
public interface BalanceWarnSettingMapper {
    /**
     * 得到所有的供应商数据
     * @return
     */
    List<SdbBusinessStoremanger> getSupplierInfo();

    /**
     * 保存所有供应商数据
     */
    void insertSupplierInfo(BalanceWarningConfig config);

    /**
     * 查询预警余额配置表中是否有数据
     * @return
     */
    int selectSupplierInfo();

    /**
     * 跟新status
     * @param id
     * @return
     */
    int updateBWByStatus(String id,String status);

}
