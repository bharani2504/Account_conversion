package org.bank.account.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bank.account.exception.DataException;
import org.bank.account.model.User;
import org.bank.account.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


 public class LoginServlet extends HttpServlet {


    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService = new UserService();
    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
       try{ User user = objectMapper.readValue(request.getInputStream(), User.class);
        userService.addUser(user);
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        response.getWriter().write("User Registered Successfully");
        LOG.info("Registered Successfully");
       }
       catch (Exception e){
           response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
           throw new DataException("Failed",e);
       }
       }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {

            String username =request.getParameter("username");
            String password= request.getParameter("password");
            String token = userService.verify(username,password);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.getWriter().write("Token: " + token);
            LOG.info("Token Generated");
        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new DataException("Failed",e);
        }
    }
}
