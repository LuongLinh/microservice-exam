package dtn.com.account_service.service;

import dtn.com.account_service.entity.AccountEntity;
import dtn.com.account_service.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<AccountEntity> getAllAccounts();
}
