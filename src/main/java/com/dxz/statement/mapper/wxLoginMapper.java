package com.dxz.statement.mapper;

import com.dxz.statement.POJO.wxLogin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface wxLoginMapper {

    int insertLoginrecord(wxLogin wxLogin);
}
