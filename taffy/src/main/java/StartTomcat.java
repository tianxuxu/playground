import ch.ralscha.embeddedtc.EmbeddedTomcat;

public class StartTomcat {

	public static void main(final String[] args) {
		EmbeddedTomcat.create().removeDefaultServlet().startAndWait();
	}
}
