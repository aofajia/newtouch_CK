package com.ruoyi.web.controller.system;

import com.ruoyi.system.domain.Storemanger;
import com.ruoyi.system.service.IOrderCheckingService;
import com.ruoyi.web.core.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 订单对账
 * 
 * @author
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequestMapping("/fi/orderchecking")
public class OrderCheckingController extends BaseController
{
    @Autowired
    private IOrderCheckingService orderCheckingService;

    @PostMapping("/storelist")
    @ResponseBody
    public List<Storemanger> list()
    {
        startPage();
        List<Storemanger> storemangers = orderCheckingService.selectStoreAll();
        return storemangers;
    }

}