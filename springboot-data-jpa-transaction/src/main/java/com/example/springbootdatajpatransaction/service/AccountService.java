package com.example.springbootdatajpatransaction.service;

import com.example.springbootdatajpatransaction.exception.TransactionException;
import com.example.springbootdatajpatransaction.dto.AccountDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    /**
     * 转账服务
     *
     * @param from   转出账号
     * @param to     转入账号
     * @param amount 转账额度
     * @throws TransactionException
     */
    void transfer(Long from, Long to, BigDecimal amount) throws TransactionException;

    /**
     * 入账
     *
     * @param id     账号ID
     * @param amount 入账额度(可为负数, 表示扣除)
     * @throws TransactionException
     */
    void addAmount(Long id, BigDecimal amount) throws TransactionException;

    /**
     * 获取账号列表
     *
     * @return
     */
    List<AccountDto> listBankAccount();
}
