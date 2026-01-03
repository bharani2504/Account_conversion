package org.bank.account.service;

import org.bank.account.dao.CustomerDAO;
import org.bank.account.model.Customer;

public class CustomerService {

    CustomerDAO customerDAO=new CustomerDAO();

    public void insert(Customer customer){
        customerDAO.insert(customer);
    }


}
