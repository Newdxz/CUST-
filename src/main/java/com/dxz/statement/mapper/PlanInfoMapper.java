package com.dxz.statement.mapper;

import com.dxz.statement.POJO.PlanInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanInfoMapper {


    List<PlanInfo> selecAll();
}
