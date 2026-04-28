package com.bytedance.douyinclouddemo.application.dto.command;

import lombok.Data;

@Data
public class OrderInfoCmd {
    private String timestamp;
    private String nonce;
    private String msg;
    private String signature;
    private String echostr;
}
