package com.ruoyi.system.service;

import com.ruoyi.system.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单对账业务层
 * 
 * @author
 */
@Repository
public interface IBalanceWarningService
{
    /**
     * 查询所有配置类型
     *
     * @return 配置类型列表
     */
    public List<BWConfigtype> selectConfigTypeAll();

    /**
     * 检测供应商名称是否存在
     *
     * @return //1为已存在  0为不存在
     */
    public String checkStoreName(String configname);
    /**
     * 新增供应商配置设置
     *
     * @return
     */
    public int addStoreConfig(BWConfig configMap);
    /**
     * 新增商城负责人配置
     *
     * @return
     */
    public int addMangeConfig(BWConfig configMap);

    /**
     * 查询所有配置信息
     *
     * @return 配置信息列表
     */
    public List<BWConfig> selectConfigAll();
    /**
     * 通过配置ID查询供应商配置
     *
     * @param roleId 行ID
     * @return 供应商配置信息
     */
    public BWConfig selectStoreConfigById(String roleId);
    /**
     * 通过配置ID查询商城负责人配置
     *
     * @param roleId 行ID
     * @return 商城负责人配置信息
     */
    public BWConfig selectManageConfigById(String roleId);
    /**
     * 修改保存供应商配置信息
     *
     * @param bwConfig 供应商配置信息
     * @return 结果
     */
    public int updateStoreConfig(BWConfig bwConfig);
    /**
     * 修改保存商城负责人配置信息
     *
     * @param bwConfig 商城负责人配置信息
     * @return 结果
     */
    public int updateManageConfig(BWConfig bwConfig);
    /**
     * 查询商城月度总消费金额配置信息
     *
     * @return 配置信息列表
     */
    public List<StoreConfig> getStoreMonthlyMoney();
    /**
     * 删除配置信息
     *
     * @param id 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    public int deleteConfigByIds(String id) throws Exception;
    /**
     * 查询所有法人体
     *
     * @return 配置信息列表
     */
    public List<Company> companyList();
    /**
     * 新增充值记录
     *
     * @return
     */
    public int saveRecharge(RechargeLog rechargeLogMap);
    /**
     * 查询充值记录信息
     *
     * @return 充值记录信息
     */
    public List<RechargeLog> getRechargeList(RechargeLog rechargeLogMap);
}
