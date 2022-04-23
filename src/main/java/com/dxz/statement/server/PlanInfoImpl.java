package com.dxz.statement.server;

import com.dxz.statement.POJO.PlanInfo;
import com.dxz.statement.POJO.UserPlan;
import com.dxz.statement.mapper.PlanInfoMapper;
import com.dxz.statement.mapper.UserPlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanInfoImpl {

    @Autowired
    PlanInfoMapper planInfoMapper;

    @Autowired
    UserPlanMapper userPlanMapper;

    public List<PlanInfo> selecAll() {
        return planInfoMapper.selecAll();
    }


    public void updateplan(UserPlan plan) {
        userPlanMapper.updateUserPlan(plan);
    }


}
