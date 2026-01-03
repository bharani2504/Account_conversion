package org.bank.account.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bank.account.model.Customer;
import org.bank.account.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {

    private static final long serialVersionUID=1L;

    static Logger log= LoggerFactory.getLogger(CustomerServlet.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

     private final CustomerService customerService=new CustomerService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){

        try{
            Customer customer=objectMapper.readValue(request.getInputStream(), Customer.class);
            customerService.insert(customer);
            response.setStatus(HttpServletResponse.SC_CREATED);
            log.info("Customer created successfully:{}",customer.getCustomerId());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            log.error("Failed to insert value in Customer, Exceptiom : ",e);
        }


    }
}
