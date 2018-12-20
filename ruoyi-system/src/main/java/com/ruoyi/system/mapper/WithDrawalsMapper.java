package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.WithDrawals;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WithDrawalsMapper {
    int deleteByPrimaryKey(Integer withdrawNo);

    int insert(WithDrawals record);

    int insertSelective(WithDrawals record);

    WithDrawals selectByPrimaryKey(Integer withdrawNo);

    int updateByPrimaryKeySelective(WithDrawals record);

    int updateByPrimaryKey(WithDrawals record);

    List<WithDrawals> list(@Param("id") String id);
}