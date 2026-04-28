package com.bytedance.douyinclouddemo.interfaces.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class TradeStatusController {

    @GetMapping("/ping")
    public String ping() {
        return "Status Code 200";
    }

}
