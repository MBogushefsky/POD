package com.bogaware.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bogaware.global.Global;

public class ApplicationStartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		System.out.println("Context started. ");
		Global global = new Global();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		System.out.println("Context ended. ");
	}

}
