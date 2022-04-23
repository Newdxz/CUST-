package com.dxz.statement.Controller;

import com.dxz.statement.POJO.wxUser;
import com.dxz.statement.config.MyResultMap;
import com.dxz.statement.server.wxUserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class wxUserController {

    @Autowired
    private wxUserImpl wxUserImpl;

    @PostMapping("/login")
    public MyResultMap getUserInfo(@RequestBody wxUser wxUsers) {
        MyResultMap resultMap = wxUserImpl.insertUser(wxUsers);
        if ("success".equals(resultMap.get("result"))) {
            return new MyResultMap().success();
        } else {
            return new MyResultMap().fail();
        }
    }


}
