package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.BWConfig;
import com.ruoyi.system.domain.BWConfigtype;
import com.ruoyi.system.domain.StoreConfig;
import com.ruoyi.system.mapper.BWConfigMapper;
import com.ruoyi.system.mapper.BWConfigtypeMapper;
import com.ruoyi.system.service.IBalanceWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class BalanceWarningServiceImpl implements IBalanceWarningService
{
    @Autowired
    private BWConfigtypeMapper bwConfigtypeMapper;
    @Autowired
    private BWConfigMapper bwConfigMapper;

    @Override
    public List<BWConfigtype> selectConfigTypeAll()
    {
        List<BWConfigtype> bwConfigtypes = bwConfigtypeMapper.selectConfigTypeAll();
        return bwConfigtypes;
    }

    @Override
    public String checkStoreName(String configname)
    {
        Map map = bwConfigtypeMapper.checkStoreName(configname);
        String count = map.get("COUNT(0)").toString();
        if("0".equals(count))
        {
            //供应商不存在 报错
        }
        return "0";
    }

    @Override
    public int addStoreConfig(BWConfig configMap)
    {
        configMap.setId(UUID.randomUUID().toString().toUpperCase());
        configMap.setConfigtype("0001");
        String configName = configMap.getConfigname();
        Map map = bwConfigMapper.checkConfigName(configName);
        String count = map.get("COUNT(0)").toString();
        if("0".equals(count))
        {
            //该供应商配置不存在
            int insert = bwConfigMapper.insert(configMap);
            return insert;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public int addMangeConfig(BWConfig configMap)
    {
        configMap.setId(UUID.randomUUID().toString().toUpperCase());
        configMap.setConfigtype("0002");
        String configName = configMap.getConfigname();
        Map map = bwConfigMapper.checkConfigName(configName);
        String count = map.get("COUNT(0)").toString();
        if("0".equals(count))
        {
            //该商城负责人配置不存在
            int insert = bwConfigMapper.insert(configMap);
            return insert;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public List<BWConfig> selectConfigAll()
    {
        List<BWConfig> bwConfigs = bwConfigMapper.selectConfigAll();
        return bwConfigs;
    }

    @Override
    public BWConfig selectStoreConfigById(String roleId)
    {
        BWConfig bwConfig = bwConfigMapper.selectByPrimaryKey(roleId);
        return bwConfig;
    }

    @Override
    public BWConfig selectManageConfigById(String roleId)
    {
        BWConfig bwConfig = bwConfigMapper.selectByPrimaryKey(roleId);
        return bwConfig;
    }

    @Override
    public int updateStoreConfig(BWConfig bwConfig)
    {
        Map map = bwConfigMapper.checkConfigNameExceptMe(bwConfig);
        String count = map.get("COUNT(0)").toString();
        if("0".equals(count))
        {
            int i = bwConfigMapper.updateByPrimaryKey(bwConfig);
            return i;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public int updateManageConfig(BWConfig bwConfig)
    {
        Map map = bwConfigMapper.checkConfigNameExceptMe(bwConfig);
        String count = map.get("COUNT(0)").toString();
        if("0".equals(count))
        {
            int i = bwConfigMapper.updateByPrimaryKey(bwConfig);
            return i;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public List<StoreConfig> getStoreMonthlyMoney()
    {
        List<StoreConfig> storeConfigs = bwConfigMapper.selectStoreConfigAll();
        return storeConfigs;
    }

    @Override
    public int deleteConfigByIds(String id) throws Exception
    {
        int i = bwConfigMapper.deleteByPrimaryKey(id);
        return i;
    }
}
