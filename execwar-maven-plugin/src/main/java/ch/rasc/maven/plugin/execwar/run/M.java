package ch.rasc.maven.plugin.execwar.run;

import java.io.InputStream;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.deploy.ContextEnvironment;
import org.apache.catalina.deploy.ContextResource;
import org.yaml.snakeyaml.Yaml;

import ch.rasc.maven.plugin.execwar.run.config.Config;
import ch.rasc.maven.plugin.execwar.run.config.Context;

public class M {
	public static void main(String[] args) throws Exception {
		try (InputStream is = M.class.getResourceAsStream("/config_default.yaml")) {
			Yaml yaml = new Yaml();
			Config config = yaml.loadAs(is, Config.class);

			for (Connector con : config.createConnectorObjects()) {
				System.out.println(con);
			}

			for (Context ctx : config.getContexts()) {
				System.out.println(ctx);
				for (ContextEnvironment ce : ctx.getEnvironments()) {
					System.out.println(ce);
				}
				for (ContextResource cr : ctx.createContextResourceObjects()) {
					System.out.println(cr);
				}
			}
		}
	}
}
