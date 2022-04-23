package com.dxz.statement.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class MyResultMap extends HashMap<String, Object> {
    public MyResultMap() {
    }

    public MyResultMap success() {
        this.put("result", "success");
        return this;
    }

    public MyResultMap fail() {
        this.put("result", "fail");
        return this;
    }

    public MyResultMap code(int code) {
        this.put("code", code);
        return this;
    }

    public MyResultMap message(Object message) {
        this.put("message", message);
        return this;
    }
}
