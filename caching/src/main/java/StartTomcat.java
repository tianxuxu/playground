import ch.ralscha.embeddedtc.EmbeddedTomcat;

public class StartTomcat {

	public static void main(String[] args) {
		EmbeddedTomcat.create().startAndWait();

	}
}
