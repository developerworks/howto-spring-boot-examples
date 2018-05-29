package com.example.springbootdatajpatransaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 转账传输对象, 用于获取客户端转账表单的数值
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferDto {
    private Long from;
    private Long to;
    private BigDecimal amount;
}
