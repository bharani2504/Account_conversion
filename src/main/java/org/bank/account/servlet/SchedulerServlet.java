package org.bank.account.servlet;

import org.bank.account.scheduler.ConversionScheduler;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

public class SchedulerServlet extends HttpServlet {

    @Override
    public void init() {
        new ConversionScheduler().start();
    }

}
