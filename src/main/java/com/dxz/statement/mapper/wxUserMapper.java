package com.dxz.statement.mapper;

import com.dxz.statement.POJO.wxUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface wxUserMapper {

    wxUser selectbyid(String wxid);

    int insertuser(wxUser wxo);

}
