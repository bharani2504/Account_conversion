package org.bank.account.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.time.LocalDateTime;

public class ConversionScheduler implements ServletContextListener {
    private static final Logger log= LoggerFactory.getLogger(ConversionScheduler.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try{

            StdSchedulerFactory factory = new StdSchedulerFactory();
             Scheduler scheduler = factory.getScheduler();

            JobDetail job = JobBuilder.newJob(Jobs.class)
                    .withIdentity("Jobs", "group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("appointmentDateTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?")).build();

            scheduler.start();
            log.info("Scheduler running at {}", LocalDateTime.now());
            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

    }
}
