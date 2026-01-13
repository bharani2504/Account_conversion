package org.bank.account.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class RegisterFilter implements Filter {


    private  static final Logger log = LoggerFactory.getLogger(RegisterFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        log.info("new request have come in");
        chain.doFilter(request,response);
        log.info("Response have been sent");

    }
}
