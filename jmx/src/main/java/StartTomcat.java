import ch.ralscha.embeddedtc.EmbeddedTomcat;

public class StartTomcat {
	public static void main(final String[] args) throws Exception {
		EmbeddedTomcat et = new EmbeddedTomcat();
		et.startAndWait();
	}
}