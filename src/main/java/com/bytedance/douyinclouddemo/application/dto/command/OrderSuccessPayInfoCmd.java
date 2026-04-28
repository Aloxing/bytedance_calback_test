package com.bytedance.douyinclouddemo.application.dto.command;

import lombok.Data;

@Data
public class OrderSuccessPayInfoCmd {
    private String appid;
    private String cp_orderno;
    private String cp_extra;
    private String order_no_channel;
    private Long amount_cent;
    private Long amount_coin;
    private String currency;
}
