package org.bank.account.scheduler;

import org.bank.account.exception.DataException;
import org.bank.account.model.Customer;
import org.bank.account.service.AccountService;
import org.bank.account.service.CustomerService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Jobs implements Job {


    private final CustomerService customerService=new CustomerService();
    private final AccountService accountService=new AccountService();


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        try {
            List<Customer> customers =  customerService.findAll();

            for(Customer customer : customers){
                accountService.UpdateAccount(customer);
            }
        } catch (SQLException e) {
            throw new DataException("failed to get the details",e);
        }
    }
}
