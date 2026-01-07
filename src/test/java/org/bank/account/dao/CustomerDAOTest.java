package org.bank.account.dao;


import org.bank.account.exception.DataException;
import org.bank.account.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static junit.framework.Assert.*;

public class CustomerDAOTest {

    private CustomerDAO customerDAO;


    @BeforeEach
    void setup() throws Exception {

        customerDAO = new CustomerDAO();

        try (
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Account_conversion", "root", "2006");) {
            Statement stmt = con.createStatement();
            stmt.execute("Delete from nominee");
            stmt.execute("Delete from account");
            stmt.execute("Delete from customer");
        }

    }

    @Test
    void testInsert() throws SQLException {

        Customer customer = createCustomer();

        customerDAO.insert(customer);

        List<Customer> customers = customerDAO.findAll();
        assertEquals(1, customers.size());
        assertEquals("Bharani", customers.get(0).getCustomerName());
    }



    @Test
    void testFindAll() throws SQLException {

        customerDAO.insert(createCustomer());

        List<Customer> customers = customerDAO.findAll();

        assertFalse(customers.isEmpty());
    }

    private Customer createCustomer() {

        Customer customer = new Customer();

        customer.setCustomerName("Bharani");
        customer.setDateOfBirth(LocalDate.of(2000, 1, 15));
        customer.setGender("MALE");
        customer.setPhoneNo("6938727120");
        customer.setEmail("bharanidharan963@gmail.com");
        customer.setAddress("Chennai, Tamil Nadu");
        customer.setAadharNo("26671541044");
        customer.setCustomerStatus("Active");

        return customer;
    }
}


