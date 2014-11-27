package com.everis.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(Log4jServlet.class);
	private static final String METHOD_TEST = "test";
	private static final String METHOD_RELOAD = "reloadLog4J";
	
    private void reloadLog4J() {

        String rootCategory = "INFO,stdout,LOGFILE";
        String fileLog = "/pr/scorating/online/pe/web/log";
        String sizeLog = "1024kb";
        String maxFilesLog = "20";

        try {
//            rootCategory = configuracionGeneralService.obtenerParametro("ROOT_CATEGORY");
//            fileLog = configuracionGeneralService.obtenerParametro("FILE_LOG");
//            sizeLog = configuracionGeneralService.obtenerParametro("SIZE_LOG");
//            maxFilesLog = configuracionGeneralService.obtenerParametro("MAX_FILES_LOG");
        } catch (Exception e) {
            LOG.error("Error:",e);
            rootCategory = "INFO,stdout,LOGFILE";
            fileLog = "/pr/scorating/online/pe/web/log";
            sizeLog = "1024kb";
            maxFilesLog = "20";
        }

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

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		reloadLog4J();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

        if(METHOD_TEST.equalsIgnoreCase(method)) {
//            try {
//                ConfiguracionGeneralService configuracionGeneralService = (ConfiguracionGeneralService) WebServletContextListener.getApplicationContext().getBean("configuracionGeneralService");
//                String param = configuracionGeneralService.obtenerParametroTest("TIEMPO_RESPUESTA");
//                if(param != null) {
//                    LOGGER.info(param);
//                }
//
//                out.write("Ok");
//            } catch(BeansException e) {
//                out.write("FAIL-SPRING");
//                LOGGER.error(e);
//            } catch(DataAccessException e) {
//                out.write("FAIL-CONNECTION-ORACLE");
//                LOGGER.error(e);
//            } catch(Exception e) {
//                out.write("FAIL-APP");
//                LOGGER.error(e);
//            }
        } else if(METHOD_RELOAD.equalsIgnoreCase(method)) {
            reloadLog4J();
            out.write("Actualizado");
        }
		
		out.close();
	}
}