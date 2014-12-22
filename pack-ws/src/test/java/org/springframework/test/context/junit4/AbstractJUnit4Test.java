package org.springframework.test.context.junit4;

import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AbstractJUnit4Test {
	
	protected Logger LOGGER = null;
	
	@Before
	public void setUp() {
		
		Properties prop = new Properties();
		prop.setProperty("log4j.rootCategory", "INFO,LOGFILE,stdout");
		prop.setProperty("log4j.appender.LOGFILE", "org.apache.log4j.RollingFileAppender");
		prop.setProperty("log4j.appender.LOGFILE.file", "/pr/pack-ws/online/pe/web/log/log_pack-ws.log");
		prop.setProperty("log4j.appender.LOGFILE.MaxFileSize", "1024kb");
		prop.setProperty("log4j.appender.LOGFILE.MaxBackupIndex", "10");
		prop.setProperty("log4j.appender.LOGFILE.append", "true");
		prop.setProperty("log4j.appender.LOGFILE.layout", "org.apache.log4j.PatternLayout");
		prop.setProperty("log4j.appender.LOGFILE.layout.ConversionPattern", "[%d{yyyy-MM-dd HH:mm:ss}] - [%5p] (%C{1}.%M:%L) - %m%n");
		prop.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
		prop.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
		prop.setProperty("log4j.appender.stdout.layout.ConversionPattern","[%d{HH:mm:ss}]%p - %C{1}.%M(%L)  %m%n");	            
		prop.setProperty("log4j.logger.org.hibernate.SQL", "DEBUG");
		// prop.setProperty("log4j.logger.org.hibernate.type", "TRACE");
		
		PropertyConfigurator.configure(prop);
		LOGGER = Logger.getLogger(AbstractJUnit4Test.class);
		LOGGER.error("Logger -> Init");
	}
	
	public void prettyPrinter(Object o) {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json =  gson.toJson(o);
			LOGGER.info(json);
		} catch(Exception e) {
			LOGGER.error("", e);
		}
	}

    public void printer(Collection<?> o) {
        try {
            Gson gson = new GsonBuilder().create();
            for(Object iO : o) {
                LOGGER.info(gson.toJson(iO));
            }
        } catch(Exception e) {
            LOGGER.error("", e);
        }
    }
}
