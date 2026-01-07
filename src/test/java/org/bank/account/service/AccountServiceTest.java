package org.bank.account.service;

import org.bank.account.dao.AccountDAO;
import org.bank.account.dao.NomineeDAO;
import org.bank.account.model.Account;
import org.bank.account.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private AccountService accountService;
    private AccountDAO accountDAO;
    private NomineeDAO nomineeDAO;

    @BeforeEach
    void setup() throws Exception {

        accountService = new AccountService();
        accountDAO = mock(AccountDAO.class);
        nomineeDAO=mock(NomineeDAO.class);

        Field accountfield = AccountService.class.getDeclaredField("accountDAO");
        accountfield.setAccessible(true);
        accountfield.set(accountService, accountDAO);


        Field nomineefield =AccountService.class.getDeclaredField("nomineeDAO");
        nomineefield.setAccessible(true);
        nomineefield.set(accountService,nomineeDAO);
    }

    @Test
    void testInsert() {

        Account account = new Account();

        accountService.insert(account);
        verify(accountDAO).insert(account);
    }

    @Test
    void doGet() throws SQLException {

        List<Account> accountList = List.of(new Account());
        when(accountDAO.getAccountsByCustomerId(1)).thenReturn(accountList);

        Object acc = accountService.getAccountByCustomerId(1);
        assertEquals(acc,accountList);
        verify(accountDAO).getAccountsByCustomerId(1);
    }


    @Test
    void testUpdateAccount_Above18() throws SQLException {

        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setDateOfBirth(LocalDate.now().minusYears(20));

        when(accountDAO.UpdateAccount(1)).thenReturn(1);

        accountService.UpdateAccount(customer);

        verify(accountDAO).UpdateAccount(1);
        verify(nomineeDAO).getNomineeByAccountId(1);
    }

    @Test
    void testUpdateAccount_Below18() throws SQLException {

        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setDateOfBirth(LocalDate.now().minusYears(18));

        when(accountDAO.UpdateAccount(1)).thenReturn(1);

        accountService.UpdateAccount(customer);

        verify(accountDAO).UpdateAccount(1);
        verify(nomineeDAO).getNomineeByAccountId(1);
    }
}
