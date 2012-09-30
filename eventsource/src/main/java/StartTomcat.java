import ch.rasc.embeddedtc.EmbeddedTomcat;

public class StartTomcat {

	public static void main(String[] args) {
		String skipJars = "spring*.jar,cglib*.jar,slf4j*.jar,logback*.jar,jcl-over*.jar,urlrewrite*.jar,hamcrest*.jar,";
		skipJars += "jackson*.jar,hibernate*.jar,jboss*.jar,guava*.jar,commons-*.jar,opencsv*.jar,joda*.jar,imgscalr*.jar,";
		skipJars += "ecj*.jar,rome*.jar,jdom*.jar";
		EmbeddedTomcat.create().skipJarsContextConfig(skipJars).startAndWait();
	}
}
