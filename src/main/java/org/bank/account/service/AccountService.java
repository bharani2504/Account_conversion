package org.bank.account.service;

import org.bank.account.dao.AccountDAO;
import org.bank.account.dao.NomineeDAO;
import org.bank.account.model.Account;
import org.bank.account.model.Customer;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class AccountService {

    AccountDAO accountDAO=new AccountDAO();
    NomineeDAO nomineeDAO=new NomineeDAO();

    public void insert(Account account){
        accountDAO.insert(account);
    }

    public List<Account> getAccountByCustomerId(long custoerId) throws SQLException {
        return accountDAO.getAccountsByCustomerId(custoerId);
    }


    public void UpdateAccount(Customer customer) throws SQLException {
        if(customer==null || customer.getDateOfBirth()==null){
            return;
        }

        LocalDate Dob=customer.getDateOfBirth();
        int age= Period.between(Dob,LocalDate.now()).getYears();

        if(age>=18){
            int updated=accountDAO.UpdateAccount(customer.getCustomerId());
            if(updated>0){
                nomineeDAO.UpdateNominee(customer.getCustomerId());
            }

        }
    }
}
