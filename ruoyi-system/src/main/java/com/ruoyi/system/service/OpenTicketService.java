package com.ruoyi.system.service;

import com.ruoyi.system.domain.OpenTicketInfoCollect;

import java.util.List;

public interface OpenTicketService {

    /**
     *  开票信息汇总
     */
    public List<OpenTicketInfoCollect> list();
}
