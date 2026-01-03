package org.bank.account.service;

import org.bank.account.dao.AccountDAO;
import org.bank.account.dao.CustomerDAO;
import org.bank.account.model.Account;
import org.bank.account.model.Customer;

public class AccountService {

    AccountDAO accountDAO=new AccountDAO();

    public void insert(Account account){
        accountDAO.insert(account);
    }
}
