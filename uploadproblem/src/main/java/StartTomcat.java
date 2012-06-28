import ch.ralscha.embeddedtc.EmbeddedTomcat;

public class StartTomcat {

	public static void main(final String[] args) {
		EmbeddedTomcat.create().removeDefaultServlet()
		// .addInitializer(SpringServletContainerInitializer.class,
		// MyWebAppInitializer.class)

				.startAndWait();
	}

}
