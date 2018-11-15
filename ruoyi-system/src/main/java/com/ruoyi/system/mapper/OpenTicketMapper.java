package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.OpenTicketInfoCollect;

import java.util.List;

public interface OpenTicketMapper {

    List<OpenTicketInfoCollect> list();

    Integer order_num();
}
