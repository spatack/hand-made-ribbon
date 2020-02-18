package com.example.demo.services.impl;

import com.example.demo.loadbalance.HttpTemplate;
import com.example.demo.services.SayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SayServiceImpl implements SayService {

    @Autowired
    private HttpTemplate httpTemplate;

    @Override
    public String say(String content) {
        return httpTemplate.getStringRequest(content);
    }
}
