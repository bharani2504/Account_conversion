package org.bank.account.service;

import org.bank.account.dao.CustomerDAO;
import org.bank.account.model.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerService {

    CustomerDAO customerDAO=new CustomerDAO();

    public void insert(Customer customer){
        customerDAO.insert(customer);
    }

    public List<Customer> findAll() throws SQLException {
        return customerDAO.findAll();
    }

}
