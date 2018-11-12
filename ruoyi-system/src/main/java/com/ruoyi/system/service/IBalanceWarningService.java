package com.ruoyi.system.service;

import com.ruoyi.system.domain.BWConfigtype;
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
}
