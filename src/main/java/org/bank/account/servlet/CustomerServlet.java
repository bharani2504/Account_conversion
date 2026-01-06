package org.bank.account.servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.bank.account.exception.DataException;
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

    private final ObjectMapper objectMapper;

    public CustomerServlet() {
        this.objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }


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

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);

            mapCustomer(response);
            log.info("Fetched All customer details successfully");
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.error("failed to get customer,Expection: ",e);
        }

    }

    private void mapCustomer(HttpServletResponse response){
        try {
            objectMapper.writeValue(response.getWriter(), customerService.findAll());

        } catch (Exception e) {
            throw new DataException("Failed to fetch customer",e);
        }
    }
}
