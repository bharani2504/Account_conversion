package org.bank.account.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.bank.account.exception.DataException;
import org.bank.account.model.Customer;

import org.bank.account.service.CustomerService;
import org.bank.account.servlet.CustomerServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class CustomerServletTest {

    ObjectMapper objectMapper=new ObjectMapper();
    private CustomerServlet customerServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private CustomerService customerService;

    @BeforeEach
    void set() throws Exception {
        customerServlet=new CustomerServlet();
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        customerService=mock(CustomerService.class);

        Field field = CustomerServlet.class.getDeclaredField("customerService");
        field.setAccessible(true);
        field.set(customerServlet,customerService);
    }

    @Test
    void testdoPost()throws Exception{
        Customer customer=new Customer();

        customer.setCustomerId(1);
        customer.setCustomerName("Bharani");
        customer.setDateOfBirth(LocalDate.parse("2000-01-15"));
        customer.setGender("MALE");
        customer.setPhoneNo("6938727120");
        customer.setEmail("bharanidharan963@gmail.com");
        customer.setAddress("chennai,Tamil Nadu");
        customer.setAadharNo("26671541044");
        customer.setCustomerStatus("Active");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String json=objectMapper.writeValueAsString(customer);
        when(request.getInputStream()).thenReturn(inputStream(json));

        customerServlet.doPost(request,response);

        verify(customerService).insert(any(Customer.class));
        verify(response).setStatus(HttpServletResponse.SC_CREATED);

    }

    @Test
    void testdoPostInvalid() throws Exception{
        when(request.getInputStream()).thenThrow(IOException.class);

        try {
            customerServlet.doPost(request,response);
        }
        catch (DataException e){
            verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Test
    void testDoGet() throws Exception {


        StringWriter writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        customerServlet.doGet(request, response);

        verify(customerService).findAll();
    }

    private ServletInputStream inputStream(String json){
        ByteArrayInputStream bs =new ByteArrayInputStream(json.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return bs.available()==0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }


            @Override
            public int read() throws IOException {
                return bs.read();
            }
        };

    }

}
