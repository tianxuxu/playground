package ch.ralscha.tomcatembed;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.deploy.ContextEnvironment;
import org.apache.catalina.deploy.ContextResource;
import org.apache.catalina.startup.Tomcat;

public class ContextExample {

	public static void main(final String[] args) throws LifecycleException, ServletException {

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);

		tomcat.setBaseDir(".");

		Context ctx = tomcat.addWebapp("/ctx", "context");

		tomcat.enableNaming();

		ContextResource res = new ContextResource();
		res.setName("jdbc/mydb");
		res.setType("javax.sql.DataSource");
		res.setAuth("Container");

		res.setProperty("username", "dbuser");
		res.setProperty("password", "dbpassword");
		res.setProperty("driverClassName", "com.mysql.jdbc.Driver");

		res.setProperty("url", "jdbc:mysql://localhost:3306/mydb?characterEncoding=UTF-8");
		res.setProperty("maxActive", "10");
		res.setProperty("maxIdle", "3");
		res.setProperty("maxWait", "10000");
		res.setProperty("defaultAutoCommit", "false");

		ctx.getNamingResources().addResource(res);

		ContextEnvironment environment = new ContextEnvironment();
		environment.setType("java.lang.String");
		environment.setName("app/exportDir");
		environment.setValue("c:/exportdir");
		ctx.getNamingResources().addEnvironment(environment);

		tomcat.start();
		tomcat.getServer().await();
	}

}
