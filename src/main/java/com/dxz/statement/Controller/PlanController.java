package com.dxz.statement.Controller;

import com.dxz.statement.POJO.UserPlan;
import com.dxz.statement.config.MyResultMap;
import com.dxz.statement.server.PlanInfoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plan")
public class PlanController {

    @Autowired
    private PlanInfoImpl planInfo;

    @RequestMapping(value = "/selectAll", method = RequestMethod.GET)
    public MyResultMap getUserInfo() {
        return new MyResultMap().success().message(planInfo.selecAll());
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public MyResultMap updateUserplan(@RequestBody UserPlan userPlan) {
        planInfo.updateplan(userPlan);
        return new MyResultMap().success();
    }

}
