package com.ruoyi.web.controller.system;

import com.ruoyi.system.domain.StoreConfig;
import com.ruoyi.system.service.IBalanceWarningService;
import com.ruoyi.web.controller.tool.Result;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.ruoyi.web.controller.tool.ResultCode.INTERFACE_INNER_INVOKE_ERROR;
import static com.ruoyi.web.controller.tool.ResultCode.SPECIFIED_STOREMONTHLYMONEY_ISNULL;


/**
 * 新致商城fi接口
 */
@RestController
public class FiController {

    //注入新政HR业务层
    @Autowired
    private IBalanceWarningService balanceWarningService;

    //纪录日志
    Logger logger = LoggerFactory.getLogger(FiController.class);

    /**
     * 获取供应商配置信息
     *
     * @return
     */
    @RequestMapping(value = "/OutInterface/getStoreMonthlyMoney", method = RequestMethod.POST)
    public Result getStoreMonthlyMoney() {
        try {
            List<StoreConfig> list = balanceWarningService.getStoreMonthlyMoney();
            //如果查询的结果大于0返回success为0提示今天没有离职员工
            if (list.size() > 0) {
                return Result.success(list);
            }
            if (list.size() == 0) {
                return Result.failure(SPECIFIED_STOREMONTHLYMONEY_ISNULL);
            }
        } catch (Exception e) {
//            logger.debug("获取供应商配置信息失败！" + e.getMessage());
            return Result.failure(INTERFACE_INNER_INVOKE_ERROR);

        }
        return Result.success();
    }

}
