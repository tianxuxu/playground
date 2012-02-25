package ch.ralscha.noxml.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.util.StatusPrinter;

public class SetupLog implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		lc.reset();

		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(lc);
		encoder.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
		encoder.start();

		ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<ILoggingEvent>();
		consoleAppender.setContext(lc);
		consoleAppender.setEncoder(encoder);
		consoleAppender.start();

		Logger rootLogger = lc.getLogger("root");
		rootLogger.setLevel(Level.ERROR);
		rootLogger.addAppender(consoleAppender);

		lc.start();

		StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		lc.stop();
	}
}
