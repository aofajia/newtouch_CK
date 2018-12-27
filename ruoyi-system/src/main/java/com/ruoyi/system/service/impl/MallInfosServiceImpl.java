package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.RedisUtils;
import com.ruoyi.system.domain.MallInfos;
import com.ruoyi.system.mapper.MallInfosMapper;
import com.ruoyi.system.service.MallInfosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MallInfosServiceImpl implements MallInfosService {
    private static final String MALLINFOREDIS="hrfimallinfo";

    @Autowired
    MallInfosMapper  mallInfosMapper;

    /**
     * 查询配置信息
     * @return
     */
    @Override
    public MallInfos queryMallInfo(){
        Object object = RedisUtils.getObject(MALLINFOREDIS);
        MallInfos mallInfos=null;
        if(object!=null){
            mallInfos=(MallInfos) object;
        }else{
            mallInfos = mallInfosMapper.queryMallInfo();
            RedisUtils.addObject(MALLINFOREDIS,mallInfos);
        }
        return mallInfos;
    }
}