package com.bbva.packws.service.impl;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bbva.packws.domain.Solicitud;
import com.bbva.packws.domain.SolicitudCONELE;
import com.bbva.packws.service.SolicitudService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
public class SolicitudServiceImplTest {
	
	@Resource(name = "solicitudDAO")
	private SolicitudService solicitudService;

	@Test
	public void consultarSolicitudes() {
		Solicitud parametro = new SolicitudCONELE();
		
		List<Solicitud> list = solicitudService.consultarSolicitudes("","",new String[]{""},new String[]{""},parametro,1);
		Assert.assertNotNull(list);
		Assert.assertTrue(!list.isEmpty());
	}
	
	private static final Logger LOG = Logger.getLogger(SolicitudServiceImplTest.class);
	
	@Before
	  public void setUp() {
	      String rootCategory = "INFO,stdout,LOGFILE";
	      String fileLog = "/pr/packws/online/pe/web/log";
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
	      prop.setProperty("log4j.appender.stdout.layout.ConversionPattern", "[%d{HH:mm:ss}]%p - %C{1}.%M(%L) %m%n");
	      prop.setProperty("log4j.logger.org.hibernate.SQL", "DEBUG");
	      prop.setProperty("log4j.logger.org.hibernate.TYPE", "TRACE");
	  
	      PropertyConfigurator.configure(prop);
	      LOG.error("Logger -> Init");
	  } 


}
