package org.bank.account.scheduler;

import org.bank.account.model.Customer;
import org.bank.account.service.AccountService;
import org.bank.account.service.CustomerService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ConversionScheduler {

    private final CustomerService customerService=new CustomerService();
    private final AccountService accountService=new AccountService();

    public void start(){

        Timer timer=new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Scheduler running at " + LocalDateTime.now());


                try {
                    List<Customer> customers =  customerService.findAll();

                    for(Customer customer : customers){
                        accountService.UpdateAccount(customer);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        },0, 24 * 60 * 60 * 1000);
    }
}
