package ch.rasc.maven.plugin.execwar.run;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.IntrospectionUtils;

public class Config {

	private String jvmRoute;

	private boolean silent = false;

	private Set<String> listeners = new HashSet<>(Arrays.asList("org.apache.catalina.core.AprLifecycleListener",
			"org.apache.catalina.core.JasperListener", "org.apache.catalina.core.JreMemoryLeakPreventionListener",
			"org.apache.catalina.mbeans.GlobalResourcesLifecycleListener",
			"org.apache.catalina.core.ThreadLocalLeakPreventionListener"));

	private Map<String, Object> systemProperties = Collections.emptyMap();

	private List<Map<String, Object>> connectors = Collections.emptyList();

	private List<Context> contexts = Collections.emptyList();

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

	public Set<String> getListeners() {
		return listeners;
	}

	public void setListeners(Set<String> listeners) {
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

	public Map<String, Object> getSystemProperties() {
		return systemProperties;
	}

	public void setSystemProperties(Map<String, Object> systemProperties) {
		this.systemProperties = systemProperties;
	}

	private static final String CONNECTOR_PROTOCOL = "protocol";

	public List<Connector> createConnectorObjects() throws Exception {
		List<Connector> conObjects = new ArrayList<>();
		for (Map<String, Object> con : getConnectors()) {
			Connector connector = new Connector(con.get(CONNECTOR_PROTOCOL).toString());

			for (Map.Entry<String, Object> entry : con.entrySet()) {
				if (!entry.getKey().equals(CONNECTOR_PROTOCOL)) {
					IntrospectionUtils.setProperty(connector, entry.getKey(), entry.getValue().toString());
				}
			}

			conObjects.add(connector);
		}

		return conObjects;
	}

	public boolean isEnableNaming() {
		for (Context ctx : getContexts()) {
			if (!ctx.getEnvironments().isEmpty() || !ctx.getResources().isEmpty() || ctx.getContextFile() != null) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		return "Config [jvmRoute=" + jvmRoute + ", silent=" + silent + ", listeners=" + listeners
				+ ", systemProperties=" + systemProperties + ", connectors=" + connectors + ", contexts=" + contexts
				+ "]";
	}

}
