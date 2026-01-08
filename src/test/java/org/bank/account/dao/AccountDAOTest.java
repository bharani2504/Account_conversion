
package org.bank.account.dao;

import org.bank.account.model.Account;
import org.bank.account.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;


public class AccountDAOTest {

    private AccountDAO accountDAO;
    private CustomerDAO customerDAO;


    @BeforeEach
    void setup() throws Exception {

        accountDAO=new AccountDAO();
        customerDAO = new CustomerDAO();


        try (
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Account_conversion", "root", "2006");) {
            Statement stmt = con.createStatement();
            stmt.execute("Delete from nominee");
            stmt.execute("Delete from account");
        }

    }


    private long createCustomer() throws SQLException {

        Customer customer = new Customer();

        customer.setCustomerName("Bharani");
        customer.setDateOfBirth(LocalDate.of(2000, 1, 15));
        customer.setGender("MALE");
        customer.setPhoneNo("6938727120");
        customer.setEmail("bharanidharan963@gmail.com");
        customer.setAddress("Chennai, Tamil Nadu");
        customer.setAadharNo("26671541044");
        customer.setCustomerStatus("Active");

        customerDAO.insert(customer);
        List<Customer> customers = customerDAO.findAll();
        return customers.get(customers.size()-1).getCustomerId();


    }

    private Account createAccount(long customerId) {

        Account account = new Account();
        account.setAccountId(1);
        account.setCustomerId(customerId);
        account.setAccountNumber("ACC123456789");
        account.setAccountType("MINOR");
        account.setAccountStatus("ACTIVE");


        return account;
    }


    @Test
    void testInsert() throws SQLException {

        long customerId = createCustomer();
        Account account = createAccount(customerId);
        accountDAO.insert(account);

        List<Account> Accounts = accountDAO.getAccountsByCustomerId(customerId);
        assertEquals(1, Accounts.size());
        assertEquals("MINOR", Accounts.get(0).getAccountType());
    }

    @Test
    void testGetAccountsByCustomerId() throws SQLException {

        long customerId = createCustomer();

        accountDAO.insert(createAccount(customerId));

        List<Account> accounts =
                accountDAO.getAccountsByCustomerId(customerId);

        assertFalse(accounts.isEmpty());
    }

    @Test
    void testUpdate() throws SQLException {
        long customerId = createCustomer();
        accountDAO.insert( createAccount(customerId));

        accountDAO.UpdateAccount(customerId);

        Account updated = accountDAO.getAccountsByCustomerId(customerId).get(0);
        assertEquals("MAJOR", updated.getAccountType());
    }




}
