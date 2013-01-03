package ch.rasc.maven.plugin.execwar.run.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.IntrospectionUtils;


public class Config {
	
	private Shutdown shutdown;

	private String jvmRoute;

	private boolean silent = false;

	private boolean sessionPersistence = false;

	private List<String> listeners;

	private List<Map<String, Object>> connectors;

	private List<Context> contexts;

	public Shutdown getShutdown() {
		return shutdown;
	}

	public void setShutdown(Shutdown shutdown) {
		this.shutdown = shutdown;
	}

	public String getJvmRoute() {
		return jvmRoute;
	}

	public void setJvmRoute(String jvmRoute) {
		this.jvmRoute = jvmRoute;
	}

	public boolean isSilent() {
		return silent;
	}

	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	public boolean isSessionPersistence() {
		return sessionPersistence;
	}

	public void setSessionPersistence(boolean sessionPersistence) {
		this.sessionPersistence = sessionPersistence;
	}

	public List<String> getListeners() {
		return listeners;
	}

	public void setListeners(List<String> listeners) {
		this.listeners = listeners;
	}

	public List<Map<String, Object>> getConnectors() {
		return connectors;
	}

	public void setConnectors(List<Map<String, Object>> connectors) {
		this.connectors = connectors;
	}

	public List<Context> getContexts() {
		return contexts;
	}

	public void setContexts(List<Context> contexts) {
		this.contexts = contexts;
	}
	
	private static final String CONNECTOR_PROTOCOL = "protocol";
	
	public List<Connector> createConnectorObjects() throws Exception {
		List<Connector> conObjects = new ArrayList<>();
		for (Map<String,Object> con : getConnectors()) {
			Connector connector = new Connector(con.get(CONNECTOR_PROTOCOL).toString());	
			
			for (Map.Entry<String,Object> entry : con.entrySet()) {
				if (!entry.getKey().equals(CONNECTOR_PROTOCOL)) {
					IntrospectionUtils.setProperty(connector, entry.getKey(), 
							entry.getValue().toString()); 
				}
			}
			
			conObjects.add(connector);
		}
		
		return conObjects;
	}
	
}
