package org.bank.account.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bank.account.model.Nominee;
import org.bank.account.service.NomineeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/nominee")
public class NomineeServlet extends HttpServlet {

    private static final long serialVersionUID=1L;

    static Logger log= LoggerFactory.getLogger(NomineeServlet.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final NomineeService nomineeService=new NomineeService();
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){

        try{
            Nominee nominee=objectMapper.readValue(request.getInputStream(), Nominee.class);
            NomineeService.insert(nominee);
            response.setStatus(HttpServletResponse.SC_CREATED);
            log.info("Nominee created successfully:{}",nominee.getNomineeId());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            log.error("Failed to insert value in nominee, Exceptiom : ",e);
        }


    }

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response){

        try{
            long accountId=Long.parseLong(request.getParameter("accountId"));

            List<Nominee> nominee=nomineeService.getNomineeByAccountID(accountId);
            ObjectMapper mapper=new ObjectMapper();
            String json=mapper.writeValueAsString(nominee);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.getWriter().write(json);

        } catch (Exception e) {
            log.error("failed",e);
        }
    }



}

