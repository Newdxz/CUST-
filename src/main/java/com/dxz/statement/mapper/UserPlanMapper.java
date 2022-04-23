package com.dxz.statement.mapper;

import com.dxz.statement.POJO.UserPlan;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPlanMapper {

    int insertUserPlan(UserPlan user);

    int updateUserPlan(UserPlan user);

    UserPlan selectUserPlanById(String wxOpenid);
}
