package org.bank.account.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bank.account.exception.DataException;
import org.bank.account.model.Nominee;
import org.bank.account.service.NomineeService;
import org.bank.account.servlet.NomineeServlet;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NomineeServletTest {

    ObjectMapper objectMapper=new ObjectMapper();
    private NomineeServlet nomineeServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private NomineeService nomineeService;


    @BeforeEach
    void set() throws Exception {
        nomineeServlet = new NomineeServlet();
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        nomineeService=mock(NomineeService.class);

        Field field = NomineeServlet.class.getDeclaredField("nomineeService");
        field.setAccessible(true);
        field.set(nomineeServlet,nomineeService);
    }

    @Test
    void testdoPost()throws Exception{
        Nominee nominee=new Nominee();
        nominee.setNomineeId(1);
        nominee.setAccountId(1);
        nominee.setNomineeName("karpagam");
        nominee.setRelationship("Mother");
        nominee.setGuardian(true);


        String json=objectMapper.writeValueAsString(nominee);
        when(request.getInputStream()).thenReturn(inputStream(json));
    }

    @Test
    void testdoPostInvalid() throws Exception{
        when(request.getInputStream()).thenThrow(IOException.class);

        try {
            nomineeServlet.doPost(request,response);
        }
        catch (DataException e){
            verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Test
    void testDoGetValidIdReturnNominee() throws Exception {
        Nominee nominee=new Nominee();
        nominee.setAccountId(1);

        when(request.getParameter("accountId")).thenReturn("1");
        when(nomineeService.getNomineeByAccountID(1L)).thenReturn(Collections.singletonList(nominee));
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        nomineeServlet.doGet(request,response);

        verify(nomineeService).getNomineeByAccountID(1L);
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
