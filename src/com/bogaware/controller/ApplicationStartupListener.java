package com.bogaware.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bogaware.messaging.TwilioMessageManager;
import com.bogaware.util.SettingsManager;

public class ApplicationStartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		System.out.println("Context started. ");
		SettingsManager global = new SettingsManager();
		TwilioMessageManager.sendMessageByPhoneNumber("4808885436", "Hey Derick this is your boss");

	}
	
	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		System.out.println("Context ended. ");
	}

}
