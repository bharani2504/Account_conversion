package org.bank.account.Servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.bank.account.exception.DataException;
import org.bank.account.model.Account;
import org.bank.account.model.Customer;
import org.bank.account.service.AccountService;
import org.bank.account.servlet.AccountServlet;
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
import java.util.Collections;

import static org.mockito.Mockito.*;

public class AccountServletTest {

    ObjectMapper objectMapper=new ObjectMapper();
    private AccountServlet accountServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private AccountService accountService;


    @BeforeEach
    void set() throws Exception {
        accountServlet = new AccountServlet();
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        accountService=mock(AccountService.class);

        Field field = AccountServlet.class.getDeclaredField("accountService");
        field.setAccessible(true);
        field.set(accountServlet,accountService);
    }

    @Test
    void testdoPost()throws Exception{
        Account account=new Account();
        account.setAccountId(1);
        account.setCustomerId(1);
        account.setAccountNumber("257413941");
        account.setAccountType("MINOR");
        account.setAccountStatus("ACTIVE");


        String json=objectMapper.writeValueAsString(account);
        when(request.getInputStream()).thenReturn(inputStream(json));

        accountServlet.doPost(request,response);

        verify(accountService).insert(any(Account.class));
        verify(response).setStatus(HttpServletResponse.SC_CREATED);

    }

    @Test
    void testdoPostInvalid() throws Exception{
        when(request.getInputStream()).thenThrow(IOException.class);

        try {
            accountServlet.doPost(request,response);
        }
        catch (DataException e){
            verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Test
    void testDoGetValidIdReturnsAccount() throws Exception {
        Account account = new Account();
        account.setCustomerId(1);

        when(request.getParameter("customerId")).thenReturn("1");
        when(accountService.getAccountByCustomerId(1L)).thenReturn(Collections.singletonList(account));
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        accountServlet.doGet(request, response);

        verify(accountService).getAccountByCustomerId(1L);
        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);

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