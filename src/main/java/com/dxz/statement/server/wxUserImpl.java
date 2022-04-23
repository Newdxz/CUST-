package com.dxz.statement.server;

import com.dxz.statement.POJO.UserPlan;
import com.dxz.statement.POJO.wxLogin;
import com.dxz.statement.POJO.wxUser;
import com.dxz.statement.config.MyResultMap;
import com.dxz.statement.mapper.UserPlanMapper;
import com.dxz.statement.mapper.wxLoginMapper;
import com.dxz.statement.mapper.wxUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class wxUserImpl {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    wxUserMapper wxUserMapper;

    @Autowired
    wxLoginMapper wxLoginMapper;

    @Autowired
    UserPlanMapper userPlanMapper;

    public wxUser selectUserByUserId(String wxUserId) {
        return wxUserMapper.selectbyid(wxUserId);
    }

    public MyResultMap editUserPlan(UserPlan userPlan) {
        int plan = userPlanMapper.updateUserPlan(userPlan);
        if (plan == 1) {
            return new MyResultMap().success();
        } else {
            return new MyResultMap().fail();
        }
    }

    public UserPlan selectUserById(String wxUserId) {
        return userPlanMapper.selectUserPlanById(wxUserId);
    }


    public MyResultMap insertUser(wxUser wxUsers) {
        String wxopenid = wxUsers.getWxOpenid();
        wxLogin wxlogin = new wxLogin();
        wxlogin.setWxOpenid(wxopenid);
        wxlogin.setWxLotime(sdf.format(new Date()));
        // 插入登录记录
        wxLoginMapper.insertLoginrecord(wxlogin);
        /**
         * 查询登录用户是否落库,为落库及时落库;
         */
        wxUser wxUser = wxUserMapper.selectbyid(wxopenid);
        System.out.println(wxUser.toString());
        if (wxUser == null) {
            wxUserMapper.insertuser(wxUsers);
        }
        // 根据用户id查询计划列表
        UserPlan userPlan = userPlanMapper.selectUserPlanById(wxopenid);
        UserPlan plans = new UserPlan();
        if (userPlan == null) {
            plans.setWxOpenid(wxopenid);
            userPlanMapper.insertUserPlan(plans);
        }
        return new MyResultMap().success();
    }

}
