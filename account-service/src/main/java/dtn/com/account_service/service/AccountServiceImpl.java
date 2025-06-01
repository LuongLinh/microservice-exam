package dtn.com.account_service.service.impl;

import dtn.com.account_service.entity.AccountEntity;
import dtn.com.account_service.repository.AccountRepository;
import dtn.com.account_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountEntity> getAllAccounts() {
        return accountRepository.findAll();
    }
}
