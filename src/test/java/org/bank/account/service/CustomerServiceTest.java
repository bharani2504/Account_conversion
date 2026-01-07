package org.bank.account.service;

import org.bank.account.dao.CustomerDAO;
import org.bank.account.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    private CustomerService customerService;
    private CustomerDAO customerDAO;

    @BeforeEach
    void setup() throws Exception {

        customerService = new CustomerService();
        customerDAO = mock(CustomerDAO.class);


        Field field = CustomerService.class.getDeclaredField("customerDAO");
        field.setAccessible(true);
        field.set(customerService, customerDAO);

    }

    @Test
    void testInsert() {

        Customer customer = new Customer();

        customerService.insert(customer);
        verify(customerDAO).insert(customer);
    }

    @Test
    void testFindAll() throws SQLException {

        List<Customer> customers = List.of(new Customer());
        when(customerDAO.findAll()).thenReturn(customers);

        Object  result = customerService.findAll();

        assertEquals(customers, result);
        verify(customerDAO).findAll();
    }
}
