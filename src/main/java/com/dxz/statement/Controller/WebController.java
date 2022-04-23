package com.dxz.statement.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制页面跳转
 */
@Controller
@RequestMapping("/check")
public class WebController {


    /**
     * 跳转到主页
     *
     * @return
     */
    @RequestMapping("/index")
    public String personal() {
        return "index";
    }





}
