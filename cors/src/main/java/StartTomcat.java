import ch.ralscha.embeddedtc.EmbeddedTomcat;

public class StartTomcat {

	public static void main(String[] args) {
		EmbeddedTomcat.create().setPort(8080).start();
		EmbeddedTomcat.create().setPort(8081).startAndWait();
	}
}
