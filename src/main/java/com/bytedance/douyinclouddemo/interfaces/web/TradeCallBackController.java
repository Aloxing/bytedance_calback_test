package com.bytedance.douyinclouddemo.interfaces.web;


import com.bytedance.douyinclouddemo.application.dto.command.OrderInfoCmd;
import com.bytedance.douyinclouddemo.application.dto.command.OrderSuccessPayInfoCmd;
import com.bytedance.douyinclouddemo.common.util.SignUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/api")
public class TradeCallBackController {

    @GetMapping("/pay/callback")
    public String verify(
            @RequestParam String signature,
            @RequestParam String timestamp,
            @RequestParam String nonce,
            @RequestParam String msg,
            @RequestParam String echostr
    ) throws Exception {

        String token = "your_token";

        List<String> list = Arrays.asList(token, timestamp, nonce, msg);
        Collections.sort(list);

        String joinStr = String.join("", list);

        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(joinStr.getBytes(StandardCharsets.UTF_8));

        StringBuilder hex = new StringBuilder();
        for (byte b : digest) {
            String s = Integer.toHexString(0xff & b);
            if (s.length() == 1) hex.append('0');
            hex.append(s);
        }

        if (hex.toString().equals(signature)) {
            return echostr;
        }

        return null;
    }

    @PostMapping("/pay/callback")
    public String payCallback(@RequestBody OrderInfoCmd oi) throws Exception {

        String token = "your_token";

        // 1️⃣ 验签
        String sign = SignUtil.sha1(
                token,
                oi.getTimestamp(),
                oi.getNonce(),
                oi.getMsg()
        );

        if (!sign.equals(oi.getSignature())) {
            return "";
        }

        // 2️⃣ 解析 msg（内层订单）
        ObjectMapper mapper = new ObjectMapper();
        OrderSuccessPayInfoCmd order =
                mapper.readValue(oi.getMsg(), OrderSuccessPayInfoCmd.class);

        System.out.println("订单信息：" + order);

        // 3️⃣ TODO：业务处理
        // - 更新订单状态
        // - 防重复支付（幂等）
        // - 写MySQL

        return "success";
    }

}
