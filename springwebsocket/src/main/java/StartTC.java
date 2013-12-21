import ch.rasc.embeddedtc.EmbeddedTomcat;

public class StartTC {

	public static void main(String[] args) {
		EmbeddedTomcat.create().startAndWait();
	}

}
