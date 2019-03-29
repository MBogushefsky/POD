package com.bogaware.global;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import com.bogaware.messaging.TwilioMessageManager;
import com.bogaware.plugins.PluginHandler;

//https://www.freeformatter.com/cron-expression-generator-quartz.html
//https://www.worldtimebuddy.com/united-states-arizona-phoenix-to-utc
public class TaskManager implements Job{
	private Scheduler scheduler;
	private String[][] cronExpressions = new String[][] {
		{"dailyAt8", "0 0 15 ? * * *"},
		{"dailyAt12", "0 0 19 ? * * *"},
		{"dailyAt16", "0 0 23 ? * * *"},
		{"dailyAt20", "0 0 3 ? * * *"},
		{"dailyEach4", }
	};

	public TaskManager() {
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Scheduler getScheduler() {
		return this.scheduler;
	}
	
	public void scheduleTask(String jobName, String triggerName, String cronDescription, JobDataMap dataMap) {
		try {
			JobDetail job = new JobDetail();
			job.setName(jobName);
			job.setJobDataMap(dataMap);
			job.setJobClass(TaskManager.class);
			CronTrigger trigger = new CronTrigger();
			trigger.setName(triggerName);
			trigger.setCronExpression(getCronExpressionFromDescription(cronDescription));
			scheduler.scheduleJob(job, trigger);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getCronExpressionFromDescription(String description) {
		description = description.toLowerCase();
		for(String[] index : cronExpressions) {
			if(index[0].equalsIgnoreCase(description)) {
				return index[1];
			}
		}
		return "* * 0/1 ? * * *";
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		PluginHandler handler = new PluginHandler((String)dataMap.get("PhoneNumber"), (String)dataMap.get("RequestMessage"));
		TwilioMessageManager.sendMessageByPhoneNumber((String)dataMap.get("PhoneNumber"), handler.getTextResponse());
		TwilioMessageManager.sendMessageByPhoneNumber("+14803133843", handler.getTextResponse());
	}
}
