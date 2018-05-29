package com.example.springbootdatajpatransaction.service.impl;

import com.example.springbootdatajpatransaction.dto.AccountDto;
import com.example.springbootdatajpatransaction.entity.Account;
import com.example.springbootdatajpatransaction.exception.TransactionException;
import com.example.springbootdatajpatransaction.repository.AccountRepository;
import com.example.springbootdatajpatransaction.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

//    @Autowired
//    public void setBankAccountRepository(BankAccountRepository bankAccountRepository) {
//        this.bankAccountRepository = bankAccountRepository;
//    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = TransactionException.class)
    public void transfer(Long from, Long to, BigDecimal amount) throws TransactionException {
        addAmount(to, amount);
        addAmount(from, amount.negate());
    }

    /**
     * @param id     账号ID
     * @param amount 入账额度(可为负数, 表示扣除)
     * @throws TransactionException
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addAmount(Long id, BigDecimal amount) throws TransactionException {
        // 找不到账号
        Account account = accountRepository.findById(id).orElseThrow(
            () -> new TransactionException(String.format("Account not fould %s", id))
        );
        BigDecimal newBalance = BigDecimal.valueOf(
            account.getBalance().doubleValue() + amount.doubleValue()
        );
        // 余额不足
        if (newBalance.doubleValue() < 0) {
            throw new TransactionException(
                String.format("The money in the account %s is not enough(%s)", id, account.getBalance())
            );
        }
        accountRepository.addAmount(id, amount);
//        account.setBalance(newBalance);
    }

    @Override
    public List<AccountDto> listBankAccount() {
        return accountRepository.listBankAccounts();
    }
}
