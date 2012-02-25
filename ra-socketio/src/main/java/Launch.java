import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

final class Launch {
	public static void main(String[] args) throws Exception {
		WebAppContext context = new WebAppContext("./src/main/webapp", "/");
		Server server = new Server(8080);
		server.setHandler(context);
		server.setStopAtShutdown(true);
		server.start();
	}
}
