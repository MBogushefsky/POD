package com.bogaware.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;
import org.quartz.JobDataMap;

import com.bogaware.twilio.TwilioMessageManager;
import com.bogaware.util.SettingsManager;

public class ApplicationStartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		System.out.println("Context started. ");
		SettingsManager global = new SettingsManager();
		TwilioMessageManager.sendMessageByPhoneNumber("4808885436", "Application start");
		
		JobDataMap dataMap = new JobDataMap();
		dataMap.put("PhoneNumber", "4808885436");
		dataMap.put("RequestMessage", "gma");
		SettingsManager.taskManager.scheduleTask("job", "trigger", "dailyAt8", dataMap);
		
		dataMap = new JobDataMap();
		dataMap.put("PhoneNumber", "4808885436");
		dataMap.put("RequestMessage", "hm");
		SettingsManager.taskManager.scheduleTask("job", "trigger", "dailyAt11", dataMap);
		
		dataMap = new JobDataMap();
		dataMap.put("PhoneNumber", "4808885436");
		dataMap.put("RequestMessage", "ab");
		SettingsManager.taskManager.scheduleTask("job", "trigger", "dailyAt20", dataMap);
		
		/*Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		// Add new Employee object
		UserAccountEntity user = new UserAccountEntity();
		user.setUsername("mbogushefsky");
		user.setPassword("password1");
		user.setFirstName("Mitchell");
		user.setLastName("Bogushefsky");
		user.setEmail("mbogushefsky@gmail.com");
		user.setPhoneNumber("4808885436");

		session.save(user);

		session.getTransaction().commit();
		HibernateUtil.shutdown();*/
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		System.out.println("Context ended. ");
	}

}
