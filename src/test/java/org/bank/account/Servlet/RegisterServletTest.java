package org.bank.account.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bank.account.model.User;
import org.bank.account.service.UserService;
import org.bank.account.servlet.RegisterServlet;
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

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class RegisterServletTest {


    ObjectMapper objectMapper =new ObjectMapper();
    private RegisterServlet registerServlet;
    private HttpServletResponse response;
    private HttpServletRequest request;
    private UserService userService;


    @BeforeEach
    void set() throws Exception{
        registerServlet =new RegisterServlet();
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        userService= mock(UserService.class);

        var field = RegisterServlet.class.getDeclaredField("userService");
        field.setAccessible(true);
        field.set(registerServlet, userService);

    }
    @Test
    void testDoPost() throws Exception {

        User user = new User();
        user.setUsername("bharani");
        user.setPassword("12345");

        String json = objectMapper.writeValueAsString(user);
        when(request.getInputStream()).thenReturn(inputStream(json));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);


        registerServlet.doPost(request,response);

        verify(userService).addUser(any(User.class));
        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    private ServletInputStream inputStream(String json) {
        ByteArrayInputStream bis =new ByteArrayInputStream(json.getBytes());
        return  new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return bis.available()==0;
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
                return bis.read();
            }
        };
    }
}
