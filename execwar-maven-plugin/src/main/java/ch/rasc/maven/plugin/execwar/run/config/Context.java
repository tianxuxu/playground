package ch.rasc.maven.plugin.execwar.run.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.catalina.deploy.ContextEnvironment;
import org.apache.catalina.deploy.ContextResource;
import org.apache.tomcat.util.IntrospectionUtils;

public class Context {
	private String war;

	private String contextPath;

	private List<Map<String, Object>> resources;

	private List<ContextEnvironment> environments;

	public String getWar() {
		return war;
	}

	public void setWar(String war) {
		this.war = war;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public List<Map<String, Object>> getResources() {
		return resources;
	}

	public void setResources(List<Map<String, Object>> resources) {
		this.resources = resources;
	}

	public List<ContextEnvironment> getEnvironments() {
		return environments;
	}

	public void setEnvironments(List<ContextEnvironment> environments) {
		this.environments = environments;
	}

	public List<ContextResource> createContextResourceObjects() {
		List<ContextResource> crObjects = new ArrayList<>();
		for (Map<String, Object> res : getResources()) {
			ContextResource resource = new ContextResource();

			for (Map.Entry<String, Object> entry : res.entrySet()) {
				IntrospectionUtils.setProperty(resource, entry.getKey(), entry.getValue().toString());
			}

			crObjects.add(resource);
		}

		return crObjects;
	}

}