package org.bank.account.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bank.account.model.Account;
import org.bank.account.model.Customer;
import org.bank.account.service.AccountService;
import org.bank.account.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID=1L;

    static Logger log= LoggerFactory.getLogger(AccountServlet.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final AccountService accountService=new AccountService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){

        try{
            Account account=objectMapper.readValue(request.getInputStream(), Account.class);
            accountService.insert(account);
            response.setStatus(HttpServletResponse.SC_CREATED);
            log.info("Customer created successfully:{}",account.getAccountId());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            log.error("Failed to insert value in Account, Exceptiom : ",e);
        }


    }
}
