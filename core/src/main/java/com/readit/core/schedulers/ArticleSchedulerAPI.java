package com.readit.core.schedulers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ArticleSchedulerAPI {

	private static final Logger LOG = LoggerFactory.getLogger(ArticleSchedulerAPI.class);
	
	@Reference
	Scheduler scheduler;

	@Activate
	protected void activate(ComponentContext componentContext) throws Exception {
		// case 1: with addJob() method: executes the job every minute
		String schedulingExpression = "*/30 * * * * ?";
		String jobName1 = "Scheduler API Job";
		Map<String, Serializable> config1 = new HashMap<String, Serializable>();
		boolean canRunConcurrently = true;
		final Runnable job1 = new Runnable() {
			public void run() {
				LOG.info("Executing ADD Job ArticleSchedulerAPI");
			}
		};
		try {
			this.scheduler.addJob(jobName1, job1, config1, schedulingExpression, canRunConcurrently);
		} catch (Exception e) {
			job1.run();
		}
		
		 String jobName2 = "case2";
         long period = 10;
         Map<String, Serializable> config2 = new HashMap<String, Serializable>();
         final Runnable job2 = new Runnable() {
             public void run() {
                 LOG.info("Executing Periodic Job ArticleSchedulerAPI");
             }
         };
         try {
             this.scheduler.addPeriodicJob(jobName2, job2, config2, period, canRunConcurrently);
         } catch (Exception e) {
             job2.run();
         }

         
       //case 3: with fireJobAt(): executes the job at a specific date (date of deployment + delay of 30 seconds)
         String jobName3 = "case3";
         final long delay = 60*1000;
         final Date fireDate = new Date();
         fireDate.setTime(System.currentTimeMillis() + delay);
         Map<String, Serializable> config3 = new HashMap<String, Serializable>();
         final Runnable job3 = new Runnable() {
             public void run() {
                 LOG.info("Executing Fire at date: {} with a delay of: {} seconds", fireDate, delay/1000);
             }
         };
         try {
             this.scheduler.fireJobAt(jobName3, job3, config3, fireDate);
         } catch (Exception e) {
             job3.run();
         }
	}

}
