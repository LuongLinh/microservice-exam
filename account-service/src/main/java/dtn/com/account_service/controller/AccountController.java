package dtn.com.account_service.controller;

import dtn.com.account_service.entity.AccountEntity;
import dtn.com.account_service.model.Account;
import dtn.com.account_service.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    private Account toModel(AccountEntity entity) {
        return new Account(
                entity.getId(),
                entity.getUsername(),
                entity.getFirstName(),
                entity.getLastName()
        );
    }
}
