package com.bbva.packws.batch;

import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
public class JobLauncherTest {
	
	private static final Logger LOG = Logger.getLogger(JobLauncherTest.class);
	
	@Resource(name = "jobLauncher")
	private JobLauncher jobLauncher;

	@Resource(name = "jobRegistry")	
	private JobRegistry jobRegistry;
	
	@Before
	public void setUp() {
	    String rootCategory = "INFO,stdout,LOGFILE";
	    String fileLog = "/pr/scorating/online/pe/web/log";
	    String sizeLog = "1024kb";
	    String maxFilesLog = "20";
	
	    Properties prop = new Properties();
	    prop.setProperty("log4j.rootCategory", rootCategory);
	    prop.setProperty("log4j.appender.LOGFILE", "org.apache.log4j.RollingFileAppender");
	    prop.setProperty("log4j.appender.LOGFILE.file", fileLog + "/log_pagcom.log");
	    prop.setProperty("log4j.appender.LOGFILE.MaxFileSize", sizeLog);
	    prop.setProperty("log4j.appender.LOGFILE.MaxBackupIndex", maxFilesLog);
	    prop.setProperty("log4j.appender.LOGFILE.append", "true");
	    prop.setProperty("log4j.appender.LOGFILE.layout", "org.apache.log4j.PatternLayout");
	    prop.setProperty("log4j.appender.LOGFILE.layout.ConversionPattern", "[%d{yyyy-MM-dd HH:mm:ss}] - [%5p] (%C{1}.%M:%L) - %m%n");
	    prop.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
	    prop.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
	    prop.setProperty("log4j.appender.stdout.layout.ConversionPattern", "[%d{HH:mm:ss}]%p - %C{1}.%M(%L)  %m%n");
	    prop.setProperty("log4j.logger.org.hibernate.SQL", "DEBUG");
	    prop.setProperty("log4j.logger.org.hibernate.TYPE", "TRACE");
	
	    PropertyConfigurator.configure(prop);
	    LOG.error("Logger -> Init");
	}
	
	@Test
	public void test() { 
		try {
	 
			JobParameters param = new JobParametersBuilder().toJobParameters(); // .addString("age", "20").toJobParameters();
			Job job = jobRegistry.getJob("jobSolicitud");
			JobExecution execution = jobLauncher.run(job, param);
			System.out.println("Exit Status : " + execution.getStatus());
			System.out.println("Exit Status : " + execution.getAllFailureExceptions());
	 
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}
}