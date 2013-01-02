package ch.rasc.maven.plugin.execwar.run;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.JasperListener;
import org.apache.catalina.core.JreMemoryLeakPreventionListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.ThreadLocalLeakPreventionListener;
import org.apache.catalina.session.StandardManager;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;

public class Runner {

	public static void main(String[] args) throws IOException {

		Path extractDir = Paths.get("tc");
		boolean extractWar = true;

		if (Files.exists(extractDir)) {
			Path timestampFile = extractDir.resolve("EXECWAR_TIMESTAMP");
			if (Files.exists(timestampFile)) {
				byte[] extractTimestampBytes = Files.readAllBytes(timestampFile);
				String extractTimestamp = new String(extractTimestampBytes, StandardCharsets.UTF_8);

				String timestamp = null;
				try (InputStream is = Runner.class.getResourceAsStream("/EXECWAR_TIMESTAMP");
						ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

					copy(is, bos);
					timestamp = new String(bos.toByteArray(), StandardCharsets.UTF_8);
				}

				if (Long.valueOf(timestamp) <= Long.valueOf(extractTimestamp)) {
					extractWar = false;
				}

			}
		}

		Path tempDir = extractDir.resolve("temp");
		final Path defaultWebxmlFile = extractDir.resolve("web.xml");
		
		if (extractWar) {
			System.out.println("EXTRACT WAR");

			try {
				if (Files.exists(extractDir)) {
					Files.walkFileTree(extractDir, new DeleteDirectory());
				}

				Files.createDirectories(extractDir);
				Files.createDirectory(tempDir);
				Files.createDirectory(extractDir.resolve("logs"));

				CodeSource src = Runner.class.getProtectionDomain().getCodeSource();
				List<String> warList = new ArrayList<>();

				if (src != null) {
					URL jar = src.getLocation();
					ZipInputStream zip = new ZipInputStream(jar.openStream());
					ZipEntry ze = null;

					while ((ze = zip.getNextEntry()) != null) {
						String entryName = ze.getName();
						if (entryName.endsWith(".war")) {
							warList.add(entryName);
						}
					}
				}

				for (String war : warList) {
					Path warFile = extractDir.resolve(war);
					try (InputStream is = Runner.class.getResourceAsStream("/" + war)) {
						Files.copy(is, warFile);
					}
				}

				try (InputStream is = Runner.class.getResourceAsStream("/conf/web.xml")) {
					Files.copy(is, defaultWebxmlFile);
				}

				Path timestampFile = extractDir.resolve("EXECWAR_TIMESTAMP");
				try (InputStream is = Runner.class.getResourceAsStream("/EXECWAR_TIMESTAMP")) {
					Files.copy(is, timestampFile);
				}

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else {
			System.out.println("NO EXTRACT NEEDED");
		}

		List<String> warAbsolutePaths = new ArrayList<>();

		try (DirectoryStream<Path> wars = Files.newDirectoryStream(extractDir, "*.war")) {
			for (Path war : wars) {
				System.out.println(war);
				warAbsolutePaths.add(war.toAbsolutePath().toString());
			}
		}

		System.setProperty("java.io.tmpdir", tempDir.toAbsolutePath().toString());
		System.out.printf("java.io.tmpdir: %s\n", System.getProperty("java.io.tmpdir"));

		// try to shutdown a runing Tomcat
		sendShutdownCommand();
		int port = 8080;
		boolean silent = false;
		boolean useNio = true;

		try {
			final ServerSocket srv = new ServerSocket(port);
			srv.close();
		} catch (IOException e) {
			// todo log.error("PORT " + port + " ALREADY IN USE");
			return;
		}

		Tomcat tomcat = new Tomcat() {

			@Override
			public Context addWebapp(@SuppressWarnings("hiding") Host host, String url, String name, String path) {
				// silence(host, url);

				Context ctx = new StandardContext();
				ctx.setName(name);
				ctx.setPath(url);
				ctx.setDocBase(path);

				ContextConfig ctxCfg = new ContextConfig();
				ctx.addLifecycleListener(ctxCfg);
				ctxCfg.setDefaultWebXml(defaultWebxmlFile.toAbsolutePath().toString());

				if (host == null) {
					getHost().addChild(ctx);
				} else {
					host.addChild(ctx);
				}

				return ctx;
			}

		};
		tomcat.setBaseDir(extractDir.toAbsolutePath().toString());

		if (silent) {
			tomcat.setSilent(true);
		}

		tomcat.getServer().addLifecycleListener(new AprLifecycleListener());

		if (useNio) {
			Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
			connector.setPort(port);
			connector.setURIEncoding("UTF-8");
			tomcat.setConnector(connector);
			tomcat.getService().addConnector(connector);
		} else {
			tomcat.setPort(port);
			tomcat.getConnector().setURIEncoding("UTF-8");
		}

		final Context ctx;
		try {
			ctx = tomcat.addWebapp("", warAbsolutePaths.iterator().next());
		} catch (ServletException e) {
			throw new RuntimeException(e);
		}

		// if (privileged) {
		// ctx.setPrivileged(true);
		// }

		// if (enableNaming || !contextEnvironments.isEmpty() ||
		// !contextResources.isEmpty()) {
		// tomcat.enableNaming();
		//
		// if (addDefaultListeners) {
		// tomcat.getServer().addLifecycleListener(new
		// GlobalResourcesLifecycleListener());
		// }
		// }

		Server server = tomcat.getServer();
		server.addLifecycleListener(new JasperListener());
		server.addLifecycleListener(new JreMemoryLeakPreventionListener());
		server.addLifecycleListener(new ThreadLocalLeakPreventionListener());

		// for (ContextEnvironment env : contextEnvironments) {
		// ctx.getNamingResources().addEnvironment(env);
		// }
		//
		// for (ContextResource res : contextResources) {
		// ctx.getNamingResources().addResource(res);
		// }

		try {
			tomcat.start();
		} catch (LifecycleException e) {
			throw new RuntimeException(e);
		}

		((StandardManager) ctx.getManager()).setPathname("");

		installSlf4jBridge();

		tomcat.getServer().await();

	}

	private static void copy(InputStream source, OutputStream sink) throws IOException {
		byte[] buf = new byte[8192];
		int n;
		while ((n = source.read(buf)) > 0) {
			sink.write(buf, 0, n);
		}
	}

	private static void sendShutdownCommand() {
		// if (shutdownPort != null) {
		// try {
		// final Socket socket = new Socket("localhost", shutdownPort);
		// final OutputStream stream = socket.getOutputStream();
		//
		// for (int i = 0; i < SHUTDOWN_COMMAND.length(); i++) {
		// stream.write(SHUTDOWN_COMMAND.charAt(i));
		// }
		//
		// stream.flush();
		// stream.close();
		// socket.close();
		// } catch (UnknownHostException e) {
		// if (!silent) {
		// log.debug(e);
		// }
		// return;
		// } catch (IOException e) {
		// if (!silent) {
		// log.debug(e);
		// }
		// return;
		// }
		//
		// // try to wait the specified amount of seconds until port becomes
		// available
		// int count = 0;
		// while (count < secondsToWaitBeforePortBecomesAvailable * 2) {
		// try {
		// final ServerSocket srv = new ServerSocket(port);
		// srv.close();
		// return;
		// } catch (IOException e) {
		// count++;
		// }
		// try {
		// TimeUnit.MILLISECONDS.sleep(500);
		// } catch (InterruptedException e) {
		// return;
		// }
		// }
		// }
	}

	private static void installSlf4jBridge() {
		try {
			// Check if slf4j bridge is available
			final Class<?> clazz = Class.forName("org.slf4j.bridge.SLF4JBridgeHandler");

			// Remove all JUL handlers
			java.util.logging.LogManager.getLogManager().reset();

			// Install slf4j bridge handler
			final Method method = clazz.getMethod("install", new Class<?>[0]);
			method.invoke(null);
		} catch (ClassNotFoundException e) {
			// do nothing
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/*
	 * private static String OS = System.getProperty("os.name").toLowerCase();
 
 
	public static boolean isWindows() {
 
		return (OS.indexOf("win") >= 0);
 
	}
	 */
	
}
