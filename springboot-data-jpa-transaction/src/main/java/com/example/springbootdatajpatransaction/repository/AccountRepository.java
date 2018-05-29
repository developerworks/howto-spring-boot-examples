package com.example.springbootdatajpatransaction.repository;

import com.example.springbootdatajpatransaction.dto.AccountDto;
import com.example.springbootdatajpatransaction.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 默认可读可写, 可以在类级别上设置默认的读写模式, 让后在方法中覆盖单个方法.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Transactional(readOnly = true)
    @Query("select new com.example.springbootdatajpatransaction.dto.AccountDto(b.id, b.name, b.balance) from Account b")
    List<AccountDto> listBankAccounts();

    @Modifying
    @Transactional
    @Query(value = "UPDATE Account b SET b.balance = b.balance + ?2 WHERE b.id = ?1")
    void addAmount(Long id, BigDecimal amount);
}
