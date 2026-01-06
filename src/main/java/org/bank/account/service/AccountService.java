package org.bank.account.service;

import org.bank.account.dao.AccountDAO;
import org.bank.account.model.Account;


import java.sql.SQLException;
import java.util.List;

public class AccountService {

    AccountDAO accountDAO=new AccountDAO();

    public void insert(Account account){
        accountDAO.insert(account);
    }

    public List<Account> getAccountByCustomerId(long custoerId) throws SQLException {
        return accountDAO.getAccountsByCustomerId(custoerId);
    }
}
