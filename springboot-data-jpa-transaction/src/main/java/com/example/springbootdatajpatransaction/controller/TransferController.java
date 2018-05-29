package com.example.springbootdatajpatransaction.controller;

import com.example.springbootdatajpatransaction.dto.AccountDto;
import com.example.springbootdatajpatransaction.dto.TransferDto;
import com.example.springbootdatajpatransaction.exception.TransactionException;
import com.example.springbootdatajpatransaction.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.List;

@Controller
@Slf4j
public class TransferController {

    private AccountDto accountDto;
    private AccountService accountService;

    @Autowired
    public void setAccountDto(AccountDto accountDto) {
        this.accountDto = accountDto;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public String showBankAccounts(Model model) {
        List<AccountDto> accounts = accountService.listBankAccount();
        model.addAttribute("accountInfos", accounts);
        return "accountsPage";
    }

    @RequestMapping(value = "/sendMoney", method = RequestMethod.GET)
    public String viewSendMoneyPage(Model model) {
        TransferDto form = new TransferDto(1L, 2L, BigDecimal.valueOf(700L));
        model.addAttribute("transferDto", form);
        model.addAttribute("errorMessage", null);
        return "sendMoneyPage";
    }

    @RequestMapping(value = "/sendMoney", method = RequestMethod.POST)
    public String processSendMoney(Model model, @ModelAttribute(value = "transferDto") TransferDto transferDto) {
        log.info("Send Money: " + transferDto.getAmount());
        try {
            accountService.transfer(transferDto.getFrom(), transferDto.getTo(), transferDto.getAmount());
        } catch (TransactionException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "/sendMoneyPage";
        }
        return "redirect:/";
    }
}
