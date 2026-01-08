package org.bank.account.dao;

import org.bank.account.model.Account;
import org.bank.account.model.Customer;
import org.bank.account.model.Nominee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class NomineeDAOTest {

    private NomineeDAO nomineeDAO;
    private AccountDAO accountDAO;
    private CustomerDAO customerDAO;

    @BeforeEach
    void setup() throws Exception {

        nomineeDAO = new NomineeDAO();
        accountDAO = new AccountDAO();
        customerDAO = new CustomerDAO();

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Account_conversion", "root", "2006")) {

            Statement stmt = con.createStatement();
            stmt.execute("DELETE FROM nominee");
            stmt.execute("DELETE FROM account");

        }
    }

    private long createCustomer() throws Exception {

        Customer customer = new Customer();
        customer.setCustomerName("Bharani");
        customer.setDateOfBirth(LocalDate.of(2000, 1, 15));
        customer.setGender("MALE");
        customer.setPhoneNo("6938727120");
        customer.setEmail("bharanidharan963@gmail.com");
        customer.setAddress("Chennai");
        customer.setAadharNo("26671541044");
        customer.setCustomerStatus("Active");

        customerDAO.insert(customer);
        return customerDAO.findAll().get(customerDAO.findAll().size() - 1).getCustomerId();
    }

    private long createAccount(long customerId) throws Exception {

        Account account = new Account();
        account.setCustomerId(customerId);
        account.setAccountNumber("ACC123456789");
        account.setAccountType("MINOR");
        account.setAccountStatus("ACTIVE");

        accountDAO.insert(account);
        return accountDAO.getAccountsByCustomerId(customerId).get(0).getAccountId();
    }

    private Nominee createNominee(long accountId) {

        Nominee nominee = new Nominee();
        nominee.setAccountId(accountId);
        nominee.setNomineeName("Father");
        nominee.setRelationship("FATHER");
        nominee.setGuardian(true);

        return nominee;
    }

    @Test
    void testInsertAndGetNomineeByAccountId() throws Exception {

        long customerId = createCustomer();
        long accountId = createAccount(customerId);

        nomineeDAO.insert(createNominee(accountId));

        List<Nominee> nominees =
                nomineeDAO.getNomineeByAccountId(accountId);

        assertFalse(nominees.isEmpty());
        assertEquals("Father", nominees.get(0).getNomineeName());
    }

    @Test
    void testUpdateNominee() throws Exception {

        long customerId = createCustomer();
        long accountId = createAccount(customerId);

        nomineeDAO.insert(createNominee(accountId));

        nomineeDAO.UpdateNominee(customerId);

        List<Nominee> nominees =
                nomineeDAO.getNomineeByAccountId(accountId);

        assertEquals(false, nominees.get(0).isGuardian());
    }
}
